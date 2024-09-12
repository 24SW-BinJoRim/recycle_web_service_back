package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageRequestDto {

    private Long userid; // 유저 DB 고유번호
    private String username; // 유저 아이디
}
