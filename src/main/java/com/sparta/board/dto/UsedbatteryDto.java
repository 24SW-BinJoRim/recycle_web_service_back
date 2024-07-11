package com.sparta.board.dto;

import com.sparta.board.entity.Clothes;
import com.sparta.board.entity.Trash;
import com.sparta.board.entity.Usedbattery;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsedbatteryDto {

    private String address; // 주소
    private String lat; // 위도
    private String lng; // 경도
    private String title; // 이름
    private String detail; // 설명
    private String type; // 종류

    public Usedbattery toEntity(){
        return Usedbattery.builder()
                .address(this.address)
                .lat(this.lat)
                .lng(this.lng)
                .title(this.title)
                .detail(this.detail)
                .type(this.type)
                .build();
    }
}
