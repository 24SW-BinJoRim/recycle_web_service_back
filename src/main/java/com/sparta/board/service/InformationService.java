package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Information;
import com.sparta.board.entity.User;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationService {

    private final InformationRepository informationRepository;

    // 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<InformationResponseDto>> getInformations() {

        List<Information> boardList = informationRepository.findAllByOrderByModifiedAtDesc();
        List<InformationResponseDto> responseDtoList = new ArrayList<>();

        for (Information information : boardList) {
            // 댓글리스트 작성일자 기준 내림차순 정렬
            information.getCommentList()
                    .sort(Comparator.comparing(Comment::getModifiedAt)
                            .reversed());

            // 대댓글은 제외 부분 작성
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : information.getCommentList()) {
                if (comment.getParentCommentId() == null) {
                    commentList.add(CommentResponseDto.from(comment));
                }
            }

            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(InformationResponseDto.from(information, commentList));
        }

        return ResponseUtils.ok(responseDtoList);
    }

    // 검색한 게시글 조회 (제목 검색)
    @Transactional(readOnly = true)
    public ApiResponseDto<List<InformationResponseDto>> searchInformation(BoardSearchDto boardSearchDto) {
        //String boardType = boardSearchDto.getBoardType();
        String keyword = boardSearchDto.getKeyword();
        log.info("Searching for keyword: {}", keyword);

        // 검색한 제목에 해당하는 게시글이 있는지 확인
        List<Information> boardList = informationRepository.findByKeyword(keyword);
        if (boardList.isEmpty()) { // 해당 게시글이 없다면
            log.warn("No results found for keyword: {}", keyword);
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        List<InformationResponseDto> responseDtoList = new ArrayList<>();

        for (Information information : boardList) {
            // 댓글리스트 작성일자 기준 내림차순 정렬
            information.getCommentList()
                    .sort(Comparator.comparing(Comment::getModifiedAt)
                            .reversed());

            // 대댓글은 제외 부분 작성
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : information.getCommentList()) {
                if (comment.getParentCommentId() == null) {
                    commentList.add(CommentResponseDto.from(comment));
                }
            }

            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(InformationResponseDto.from(information, commentList));
        }

        return ResponseUtils.ok(responseDtoList);
    }

    // 게시글 작성
    @Transactional
    public ApiResponseDto<InformationResponseDto> createInformation(BoardRequestsDto requestsDto, User user) {

        // 작성 글 저장
        Information information = informationRepository.save(Information.of(requestsDto, user));

        // BoardResponseDto 로 변환 후 responseEntity body 에 담아 반환
        return ResponseUtils.ok(InformationResponseDto.from(information));

    }

    public Information getBoard(Long id) {
        return informationRepository.findById(id).orElse(null);
    }

    public Information saveBoard(Information information) {
        return informationRepository.save(information);
    }

    public void deleteBoard(Long id) {
        informationRepository.deleteById(id);
    }
}
