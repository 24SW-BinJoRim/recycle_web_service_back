package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.common.SuccessResponse;
import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Information;
import com.sparta.board.entity.User;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.entity.enumSet.UserRoleEnum;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {

        List<Board> boardList = boardRepository.findAllByOrderByUpdatedAtDesc();
        List<BoardResponseDto> responseDtoList = new ArrayList<>();

        for (Board board : boardList) {
            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(BoardResponseDto.from(board));
        }

        return responseDtoList;
    }

    // 검색한 게시글 조회 (제목 검색)
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchPost(BoardSearchDto boardSearchDto) {
        //String boardType = boardSearchDto.getBoardType();
        String keyword = boardSearchDto.getKeyword();
        log.info("Searching for keyword: {}", keyword);

        // 검색한 제목에 해당하는 게시글이 있는지 확인
        List<Board> boardList = boardRepository.findByKeyword(keyword);

        /*
        if (boardList.isEmpty()) { // 해당 게시글이 없다면
            log.warn("No results found for keyword: {}", keyword);
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
         */
        List<BoardResponseDto> responseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(BoardResponseDto.from(board));
        }

        return responseDtoList;
    }

    // 게시글 작성
    @Transactional
    public BoardResponseDto createPost(BoardRequestsDto requestsDto, User user) {

        // 작성 글 저장
        Board board = boardRepository.save(Board.of(requestsDto, user));

        // BoardResponseDto 로 변환 후 responseEntity body 에 담아 반환
        return BoardResponseDto.from(board);
    }

    // 선택된 게시글 조회
    @Transactional(readOnly = true)
    public BoardResponseDto getPost(Long id) {
        // Id에 해당하는 게시글이 있는지 확인
        Optional<Board> board = boardRepository.findById(id);

        /*
        if (board.isEmpty()) { // 해당 게시글이 없다면
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        */
        return BoardResponseDto.from(board.get());
    }

    // 선택된 게시글 수정
    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestsDto requestsDto, User user) {

        // 선택한 게시글이 DB에 있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            log.info("선택한 게시글이 존재하지 않습니다.");
            return null;
        }

        // 선택한 게시글의 작성자와 토큰에서 가져온 사용자 정보가 일치하는지 확인 (수정하려는 사용자가 관리자라면 게시글 수정 가능)
        Optional<Board> found = boardRepository.findByIdAndUser(id, user);
        if (found.isEmpty() && user.getRole() == UserRoleEnum.USER) { // 일치하는 게시물이 없다면
            log.info("작성자와 수정자가 일치하지 않습니다.");
            return null;
        }

        // 게시글 id 와 사용자 정보 일치한다면, 게시글 수정
        board.get().update(requestsDto, user);
        boardRepository.flush(); // responseDto 에 modifiedAt 업데이트 해주기 위해 flush 사용

        return BoardResponseDto.from(board.get());

    }

    // 게시글 삭제
    @Transactional
    public Boolean deletePost(Long id, User user) {

        // 선택한 게시글이 DB에 있는지 확인
        Optional<Board> found = boardRepository.findById(id);
        if (found.isEmpty()) {
            log.info("선택한 게시글이 존재하지 않습니다.");
            return null;
        }

        // 선택한 게시글의 작성자와 토큰에서 가져온 사용자 정보가 일치하는지 확인 (삭제하려는 사용자가 관리자라면 게시글 삭제 가능)
        Optional<Board> board = boardRepository.findByIdAndUser(id, user);
        if (board.isEmpty() && user.getRole() == UserRoleEnum.USER) { // 일치하는 게시물이 없다면
            log.info("작성자와 삭제자가 일치하지 않습니다.");
            return null;
        }

        // 게시글 id 와 사용자 정보 일치한다면, 게시글 삭제
        boardRepository.deleteById(id);
        log.info("게시글 삭제 성공 !");
        return true;

    }

}
