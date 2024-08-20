package com.sparta.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();
}
