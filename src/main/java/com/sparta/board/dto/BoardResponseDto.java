package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private Long userid;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likes;
    //private List<CommentResponseDto> commentList;

    @Builder
    private BoardResponseDto(Board entity, List<CommentResponseDto> list) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.userid = entity.getUser().getUserid();
        this.username = entity.getUser().getUsername();
        this.nickname = entity.getUser().getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.likes = entity.getLikesList() != null ? entity.getLikesList().size() : 0;
        //this.commentList = list;
    }

    private BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.userid = entity.getUser().getUserid();
        this.username = entity.getUser().getUsername();
        this.nickname = entity.getUser().getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.likes = entity.getLikesList() != null ? entity.getLikesList().size() : 0;
        //this.commentList = entity.getCommentList().stream().map(CommentResponseDto::from).toList();
    }

    public static BoardResponseDto from(Board entity, List<CommentResponseDto> list) {
        return BoardResponseDto.builder()
                .entity(entity)
                .list(list)
                .build();
    }

    public static BoardResponseDto from(Board entity) {
        return new BoardResponseDto(entity);
    }

}
