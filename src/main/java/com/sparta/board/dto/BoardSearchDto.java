package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardSearchDto {
    private String boardType; // 게시판 타입
    private String keyword; // 검색 키워드

    // Getters and Setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }
}
