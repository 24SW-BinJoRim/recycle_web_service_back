package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.TrashDto;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Trash;
import com.sparta.board.repository.TrashRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapService {

    /*
    private final TrashRepository trashRepository;
    // 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<TrashDto>> getPosts() {

        List<Trash> trashList = trashRepository.findAllByOrderByNumDesc();
        List<TrashDto> responseDtoList = new ArrayList<>();

        for (Trash trash : trashList) {
            // 댓글리스트 작성일자 기준 내림차순 정렬
            trash.getCommentList()
                    .sort(Comparator.comparing(Comment::getModifiedAt)
                            .reversed());

            // 대댓글은 제외 부분 작성
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : trash.getCommentList()) {
                if (comment.getParentCommentId() == null) {
                    commentList.add(CommentResponseDto.from(comment));
                }
            }

            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(BoardResponseDto.from(trash, commentList));
        }

        return ResponseUtils.ok(responseDtoList);

    }
    */
}
