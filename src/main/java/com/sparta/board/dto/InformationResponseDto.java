package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Information;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class InformationResponseDto {
    private Long id;
    private String title;
    private String contents;
    private Long userid;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likeCount;
    //private List<CommentResponseDto> commentList;

    @Builder
    private InformationResponseDto(Information entity, List<CommentResponseDto> list) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.userid = entity.getUser().getUserid();
        this.nickname = entity.getUser().getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.likeCount = entity.getLikesList() != null ? entity.getLikesList().size() : 0;
        //this.commentList = list;
    }

    private InformationResponseDto(Information entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.userid = entity.getUser().getUserid();
        this.nickname = entity.getUser().getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.likeCount = entity.getLikesList() != null ? entity.getLikesList().size() : 0;
        //this.commentList = entity.getCommentList().stream().map(CommentResponseDto::from).toList();
    }

    public static InformationResponseDto from(Information entity, List<CommentResponseDto> list) {
        return InformationResponseDto.builder()
                .entity(entity)
                .list(list)
                .build();
    }

    public static InformationResponseDto from(Information entity) {
        return new InformationResponseDto(entity);
    }
}
