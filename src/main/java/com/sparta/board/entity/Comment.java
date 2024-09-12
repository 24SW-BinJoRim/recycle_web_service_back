package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;

    @Column(nullable = false)
    private String boardType;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = true)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "information_id", nullable = true)
    private Information information;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    public static Comment createForBoard(CommentRequestDto requestDto, Board board, User user, String boardType) {
        Comment comment = new Comment();
        comment.content = requestDto.getContent();
        comment.board = board;
        comment.user = user;
        comment.boardType = boardType;
        return comment;
    }

    public static Comment createForInformation(CommentRequestDto requestDto, Information information, User user, String boardType) {
        Comment comment = new Comment();
        comment.content = requestDto.getContent();
        comment.information = information;
        comment.user = user;
        comment.boardType = boardType;
        return comment;
    }


    public void update(CommentRequestDto requestDto, User user) {
        this.content = requestDto.getContent();
        this.user = user;
    }
}
