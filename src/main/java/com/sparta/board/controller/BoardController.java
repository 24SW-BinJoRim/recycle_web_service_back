package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.SuccessResponse;
import com.sparta.board.dto.BoardRequestsDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.BoardSearchDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/used-transaction")
public class BoardController {

    private final BoardService boardService;

    // 게시글 전체 목록 조회
    @GetMapping("/data")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }

    // 게시글 작성
    @PostMapping("/submit")
    public BoardResponseDto createPost(@RequestBody BoardRequestsDto requestsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createPost(requestsDto, userDetails.getUser());
    }

    // 선택된 게시글 조회 (제목 검색)
    @GetMapping("/search")
    public List<BoardResponseDto> searchPost( @RequestParam String boardType,
                                                            @RequestParam String keyword) {
        // DTO에 검색 조건 세팅
        BoardSearchDto boardDTO = new BoardSearchDto();
        boardDTO.setBoardType(boardType);
        boardDTO.setKeyword(keyword);

        // Service 호출
        return boardService.searchPost(boardDTO);
    }

    // 선택된 게시글 조회
    @GetMapping("/data/{boardId}")
    public BoardResponseDto getPost(@PathVariable Long boardId) {
        return boardService.getPost(boardId);
    }

    // ** 게시글 수정, 삭제는 프론트에서 미구현됨 !! **

    // 선택된 게시글 수정
    @PutMapping("/edit/{id}")
    public BoardResponseDto updatePost(@PathVariable Long id, @RequestBody BoardRequestsDto requestsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updatePost(id, requestsDto, userDetails.getUser());
    }

    // 선택된 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public Boolean deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deletePost(id, userDetails.getUser());
    }

}
