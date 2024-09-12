package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDto {
    private Long board_id; // 게시글 번호
    private String board_type; // 게시판 타입
    private Long userid ; // 유저 고유 DB id
}
