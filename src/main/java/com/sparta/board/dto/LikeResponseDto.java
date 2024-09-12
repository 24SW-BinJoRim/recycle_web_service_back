package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Likes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeResponseDto {
    private Long id;
    private Long board_id;
    private Long userid;

    @Builder
    public LikeResponseDto(Likes entity) {
        this.id = entity.getId();
        this.userid = entity.getUser().getUserid();

        if (entity.getBoard() != null) {
            this.board_id = entity.getBoard().getId();
        } else if (entity.getInformation() != null) {
            this.board_id = entity.getInformation().getId();
        }
    }

    public static LikeResponseDto from(Likes entity) {
        return LikeResponseDto.builder()
                .entity(entity)
                .build();
    }
}
