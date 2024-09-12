package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String boardType;

    @ManyToOne
    @JoinColumn(name = "information_id")
    private Information information;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static Likes likesForBoard(Board board, User user, String boardType) {
        Likes likes = new Likes();
        likes.board = board;
        likes.user = user;
        likes.boardType = boardType;
        return likes;
    }

    public static Likes likesForInformation(Information information, User user, String boardType) {
        Likes likes = new Likes();
        likes.information = information;
        likes.user = user;
        likes.boardType = boardType;
        return likes;
    }

}
