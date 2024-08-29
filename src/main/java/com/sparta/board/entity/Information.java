package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestsDto;
import com.sparta.board.dto.InformationResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Information extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    // 초기 length 값이 65535으로 테이블 생성이 안됐었으나 길이를 줄임으로써 해결
    @Column(nullable = false, length = 500)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "board_user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    private Information(BoardRequestsDto requestsDto, User user) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user = user;
    }

    public void update(BoardRequestsDto requestsDto, User user) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user = user;
    }

    public static Information of(BoardRequestsDto requestsDto, User user) {
        return Information.builder()
                .requestsDto(requestsDto)
                .user(user)
                .build();
    }
}
