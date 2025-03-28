package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.dto.BoardRequestsDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.BoardSearchDto;
import com.sparta.board.dto.InformationResponseDto;
import com.sparta.board.entity.Information;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/information")
public class InformationController {

    private final InformationService informationService;

    // 정보 게시판 전체 목록 조회
    @GetMapping("/data")
    public List<InformationResponseDto> getInformations() {
        return informationService.getInformations();
    }

    // 게시글 검색
    @GetMapping("/search")
    public List<InformationResponseDto> searchInformation(@RequestParam String boardType,
                                                                          @RequestParam String keyword) {
        // DTO에 검색 조건 세팅
        BoardSearchDto boardDTO = new BoardSearchDto();
        boardDTO.setBoardType(boardType);
        boardDTO.setKeyword(keyword);

        // Service 호출
        return informationService.searchInformation(boardDTO);
    }

    // 선택된 게시글 조회
    @GetMapping("/data/{boardId}")
    public InformationResponseDto selectInformation(@PathVariable Long boardId) {
        return informationService.selectInformation(boardId);
    }

    // 게시글 작성
    @PostMapping("/submit")
    public InformationResponseDto createInformation(@RequestBody BoardRequestsDto requestsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return informationService.createInformation(requestsDto, userDetails.getUser());
    }
}
