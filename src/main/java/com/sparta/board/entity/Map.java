package com.sparta.board.entity;

import com.sparta.board.entity.enumSet.UserRoleEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address; // 주소
    private String lat; // 위도
    private String lng; // 경도
    private String title; // 이름
    private String detail; // 설명
    private String type; // 종류

    @Builder
    private Map(String address, String lat, String lng, String title, String detail, String type) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.detail = detail;
        this.type = type;
    }

    public static Map of(String address, String lat, String lng, String title, String detail, String type) {
        return Map.builder()
                .address(address)
                .lat(lat)
                .lng(lng)
                .title(title)
                .detail(detail)
                .type(type)
                .build();
    }
}
