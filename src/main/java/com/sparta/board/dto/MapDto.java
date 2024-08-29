package com.sparta.board.dto;

import com.sparta.board.entity.Map;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapDto {

    private String address; // 주소
    private String lat; // 위도
    private String lng; // 경도
    private String title; // 이름
    private String detail; // 설명
    private String type; // 종류

    public Map toEntity(){
        return Map.builder()
                .address(this.address)
                .lat(this.lat)
                .lng(this.lng)
                .title(this.title)
                .detail(this.detail)
                .type(this.type)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapDto mapDto = (MapDto) o;
        return Objects.equals(lat, mapDto.lat) &&
                Objects.equals(lng, mapDto.lng) &&
                Objects.equals(title, mapDto.title) &&
                Objects.equals(detail, mapDto.detail) &&
                Objects.equals(type, mapDto.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng, title, detail, type);
    }
}
