package com.sparta.board.controller;

import com.sparta.board.entity.Information;
import com.sparta.board.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/information/data")
public class InformationController {

    private final InformationService informationService;

    // 정보 게시판 전체 목록 조회
    @GetMapping
    public List<Information> getAllBoards() {
        return informationService.getAllBoards();
    }

    // 선택된 게시글 조회
    @GetMapping("/{id}")
    public Information getBoard(@PathVariable Long id) {
        return informationService.getBoard(id);
    }

    // 선택된 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        informationService.deleteBoard(id);
    }
}
