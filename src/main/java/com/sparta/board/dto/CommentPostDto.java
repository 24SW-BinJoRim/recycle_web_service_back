package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostDto {
    private Long board_id; // 게시판 타입
    private String type; // 검색 키워드
}
