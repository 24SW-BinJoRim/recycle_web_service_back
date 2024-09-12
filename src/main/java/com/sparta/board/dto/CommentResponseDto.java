package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long userid;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long board_id;
    private String boardType;
    //private Integer likeCount;
    //private List<CommentResponseDto> childCommentList;

    @Builder
    private CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.username = entity.getUser().getUsername();
        this.nickname = entity.getUser().getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.userid = entity.getUser().getUserid();
        this.boardType = entity.getBoardType();
        //his.likeCount = (int) entity.getLikesList().stream().count();
        //this.childCommentList = entity.getChildCommentList().stream().map(CommentResponseDto::from).toList();

        // board와 information이 모두 null인지 확인하고 적절히 설정
        if (entity.getBoard() != null) {
            this.board_id = entity.getBoard().getId();
        } else if (entity.getInformation() != null) {
            this.board_id = entity.getInformation().getId();
        }
    }

    public static CommentResponseDto from(Comment entity) {
        return CommentResponseDto.builder()
                .entity(entity)
                .build();
    }

}
