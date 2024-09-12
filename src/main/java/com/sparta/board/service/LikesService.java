package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.LikeRequestDto;
import com.sparta.board.dto.LikeResponseDto;
import com.sparta.board.entity.*;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.InformationRepository;
import com.sparta.board.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final InformationRepository informationRepository;


    // 게시글 좋아요 기능
    public LikeResponseDto likePost(LikeRequestDto likeRequestDto, User user) {

        String boardType = likeRequestDto.getBoard_type();
        Long boardId = likeRequestDto.getBoard_id();
        Long userId = likeRequestDto.getUserid();

        // Null 체크
        if (boardType == null || boardId == null || userId == null) {
            throw new IllegalArgumentException("게시글 종류, 게시글 ID, 사용자 ID는 null이 될 수 없습니다.");
        }

        if ("used-transaction".equals(boardType)) { // 중고거래 게시판
            // 게시글 찾기
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new RestApiException(ErrorType.NOT_FOUND_WRITING));

            // 이미 좋아요를 누른 상태인지 확인
            boolean isLiked = likesRepository.findByBoardAndUser(board, user).isPresent();
            if (!isLiked) {
                Likes likes;
                likes = Likes.likesForBoard(board, user, boardType);
                likesRepository.save(likes);
                return LikeResponseDto.from(likes);
            }
        } else { // 정보 게시판
            // 정보 찾기
            Information information = informationRepository.findById(boardId)
                    .orElseThrow(() -> new RestApiException(ErrorType.NOT_FOUND_WRITING));

            // 이미 좋아요를 누른 상태인지 확인
            boolean isLiked = likesRepository.findByInformationAndUser(information, user).isPresent();
            if (!isLiked) {
                Likes likes;
                likes = Likes.likesForInformation(information, user, boardType);
                likesRepository.save(likes);
                return LikeResponseDto.from(likes);
            }
        }
        // 이미 좋아요를 누른 상태
        log.info("이미 좋아요를 누른 상태라 null 반환");
        return null;
    }

    // 게시글 좋아요 삭제 기능
    public boolean unlikePost(LikeRequestDto likeRequestDto, User user) {

        String boardType = likeRequestDto.getBoard_type();
        Long board_id = likeRequestDto.getBoard_id();
        Long userid = likeRequestDto.getUserid();

        // 선택한 게시글이 DB에 있는지 확인
        if ("used-transaction".equals(boardType)) { // 중고거래 게시판
            Optional<Board> board = boardRepository.findById(board_id);
            if (board.isEmpty()) {
                throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
            }

            // 이전에 좋아요 누른 적 있는지 확인
            Optional<Likes> found = likesRepository.findByBoardAndUser(board.get(), user);
            if (!found.isEmpty()) {  // 좋아요 누른적 있음
                likesRepository.delete(found.get()); // 좋아요 눌렀던 정보를 지운다.
                likesRepository.flush();
                log.info("성공적으로 좋아요가 삭제됨");
                return true;
            }
        }
        else { // 정보 게시판
            Optional<Information> information = informationRepository.findById(board_id);
            if (information.isEmpty()) {
                throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
            }

            // 이전에 좋아요 누른 적 있는지 확인
            Optional<Likes> found = likesRepository.findByInformationAndUser(information.get(), user);
            if (!found.isEmpty()) {  // 좋아요 누른적 없음
                likesRepository.delete(found.get()); // 좋아요 눌렀던 정보를 지운다.
                likesRepository.flush();
                log.info("성공적으로 좋아요가 삭제됨");
                return true;
            }
        }
        // 좋아요가 되지 않은 상태
        log.info("좋아요가 이미 삭제되어 null 반환");
        return false;
    }
}
