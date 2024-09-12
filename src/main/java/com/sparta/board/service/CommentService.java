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
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final InformationRepository informationRepository;

    // 특정 게시글의 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> postComment(CommentPostDto commentPostDto) {
        String Type = commentPostDto.getType();
        Long board_id = commentPostDto.getBoard_id();
        log.info("Searching for keyword: {}", board_id);

        List<CommentResponseDto> commentList = new ArrayList<>();
        // 검색한 제목에 해당하는 게시글이 있는지 확인
        if(Type == "used-transaction") { // 중고거래 게시판의 경우
            Optional<Board> optionalBoard = boardRepository.findById(board_id);

            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();

                for (Comment comment : board.getCommentList()) {
                    commentList.add(CommentResponseDto.from(comment));
                }
            }
        }
        else { // 정보게시판의 경우
            Optional<Information> optionalBoard = informationRepository.findById(board_id);

            if (optionalBoard.isPresent()) {
                Information information = optionalBoard.get();

                for (Comment comment : information.getCommentList()) {
                    commentList.add(CommentResponseDto.from(comment));
                }
            }
        }
        return commentList;
    }

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(CommentPostDto commentPostDto, CommentRequestDto requestDto, User user) {


        String Type = commentPostDto.getType();
        Long id = commentPostDto.getBoard_id();
        log.info("Searching for board_id: {}", id);

        Board board = null;
        Information information = null;

        // 선택한 게시글 DB 조회
        if("used-transaction".equals(Type)) { // 중고거래 게시판
            log.info("중고거래 게시판");
            Optional<Board> optionalBoard = boardRepository.findById(id);

            if (optionalBoard.isEmpty()) {
                log.info("중고거래 게시판 내에 해당 게시물을 찾지 못했음");
                throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
                //return null;
            }
            board = optionalBoard.get();
        }
        else { // 정보게시판
            log.info("정보 게시판");
            Optional<Information> optionalInformation = informationRepository.findById(id);
            if (optionalInformation.isEmpty()) {
                log.info("정보 게시판 내에 해당 게시물을 찾지 못했음");
                throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
                //return null;
            }
            information = optionalInformation.get();
        }

        Comment comment;
        if (board != null) {
            comment = Comment.createForBoard(requestDto, board, user, Type);
        } else {
            comment = Comment.createForInformation(requestDto, information, user, Type);
        }

        if (board == null)
            log.info("board 가 null 임");
        if (information == null)
            log.info("information 이  null 임");

        commentRepository.save(comment);
        return CommentResponseDto.from(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {

        // 선택한 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        // 댓글의 작성자와 수정하려는 사용자의 정보가 일치하는지 확인 (수정하려는 사용자가 관리자라면 댓글 수정 가능)
        Optional<Comment> found = commentRepository.findByIdAndUser(id, user);
        if (found.isEmpty() && user.getRole() == UserRoleEnum.USER) {
            throw new RestApiException(ErrorType.NOT_WRITER);
        }

        // 관리자이거나, 댓글의 작성자와 수정하려는 사용자의 정보가 일치한다면, 댓글 수정
        comment.get().update(requestDto, user);
        commentRepository.flush();   // responseDto 에 modifiedAt 업데이트 해주기 위해 saveAndFlush 사용

        // ResponseEntity 에 dto 담아서 리턴
        return CommentResponseDto.from(comment.get());

    }

    // 댓글 삭제
    @Transactional
    public CommentResponseDto deleteComment(Long id, User user) {

        // 선택한 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        // 댓글의 작성자와 삭제하려는 사용자의 정보가 일치하는지 확인 (삭제하려는 사용자가 관리자라면 댓글 삭제 가능)
        Optional<Comment> found = commentRepository.findByIdAndUser(id, user);
        if (found.isEmpty() && user.getRole() == UserRoleEnum.USER) {
            throw new RestApiException(ErrorType.NOT_WRITER);
        }

        // 관리자이거나, 댓글의 작성자와 삭제하려는 사용자의 정보가 일치한다면, 댓글 삭제
        commentRepository.deleteById(id);

        log.info("댓글 삭제 성공 !");
        return null;

    }

}
