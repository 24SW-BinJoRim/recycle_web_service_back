package com.sparta.board.dto;

public class UserRequestDto {
    private String userid; // 게시판 타입
    private String nickname; // 검색 키워드

    // Getters and Setters
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
