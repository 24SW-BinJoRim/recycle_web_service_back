package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MapResponseDto {

    private String lat; // 위도
    private String lng; // 경도
    private String title; // 이름
    private String detail; // 설명
    private String type; // 종류

    public static MapResponseDto from(MapDto mapDto) {
        return MapResponseDto.builder()
                .lat(mapDto.getLat())
                .lng(mapDto.getLng())
                .title(mapDto.getTitle())
                .detail(mapDto.getDetail())
                .type(mapDto.getType())
                .build();
    }
}