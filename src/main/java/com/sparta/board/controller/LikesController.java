package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.dto.*;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/likes")
public class LikesController {

    private final LikesService likesService;

    // 게시글 좋아요
    @PutMapping("/submit")
    public LikeResponseDto likePost(@RequestParam String board_type,
                                    @RequestParam Long board_id,
                                    @RequestParam Long userid,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Null check
        if (board_type == null || board_id == null || userid == null) {
            throw new IllegalArgumentException("Board type, board ID, and user ID must not be null");
        }

        // DTO에 검색 조건 세팅
        LikeRequestDto likeDTO = new LikeRequestDto();
        likeDTO.setBoard_type(board_type);
        likeDTO.setBoard_id(board_id);
        likeDTO.setUserid(userid);

        return likesService.likePost(likeDTO, userDetails.getUser());
    }

    @PutMapping("/delete")
    public boolean unlikePost(@RequestParam String board_type,
                                                         @RequestParam Long board_id,
                                                         @RequestParam Long userid,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // DTO에 검색 조건 세팅
        LikeRequestDto likeDTO = new LikeRequestDto();
        likeDTO.setBoard_type(board_type);
        likeDTO.setBoard_id(board_id);
        likeDTO.setUserid(userid);

        return likesService.unlikePost(likeDTO, userDetails.getUser());
    }
}
