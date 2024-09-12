package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.SuccessResponse;
import com.sparta.board.dto.BoardSearchDto;
import com.sparta.board.dto.CommentPostDto;
import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 전체 댓글 데이터
    @PostMapping("/data")
    public List<CommentResponseDto> postComment(@RequestParam Long board_id, @RequestParam String type) {
        // DTO에 검색 조건 세팅
        CommentPostDto CommentDTO = new CommentPostDto();
        CommentDTO.setBoard_id(board_id);
        CommentDTO.setType(type);

        // Service 호출
        return commentService.postComment(CommentDTO);
    }

    // 댓글 작성
    @PostMapping("/submit")   // 여기서 ID는 게시글의 id
    public CommentResponseDto createComment(@RequestParam Long board_id, @RequestParam String type, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Searching for board_id: {}", board_id);

        // DTO에 검색 조건 세팅
        CommentPostDto CommentDTO = new CommentPostDto();
        CommentDTO.setBoard_id(board_id);
        CommentDTO.setType(type);

        return commentService.createComment(CommentDTO, requestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/edit")    // 여기서 ID는 댓글의 id
    public CommentResponseDto updateComment(@RequestParam Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/delete")     // 여기서 ID는 댓글의 id
    public CommentResponseDto deleteComment(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }

}
