package com.sparta.board.repository;

import com.sparta.board.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByBoardAndUser(Board board, User user);
    Optional<Likes> findByInformationAndUser(Information information, User user);
    Optional<Likes> findByCommentAndUser(Comment comment, User user);

    // 마이페이지 - 작성한 댓글 반환 (중고 거래 게시판)
    // 좋아요 데이터에서 board id(중고거래 게시글)가 존재하지 않으면 반환하지 않음
    @Query("SELECT l.board.title, l.board.contents, l.board.id FROM Likes l " +
            "JOIN l.board b " +
            "WHERE l.user.userid = :userid")
    List<Object[]> findByUserBoardLike(@Param("userid") Long userid);

    // 마이페이지 - 작성한 댓글 반환 (정보 게시판)
    @Query("SELECT l.information.title, l.information.contents, l.information.id FROM Likes l " +
            "WHERE l.user.userid = :userid")
    List<Object[]> findByUserInfoLike(@Param("userid") Long userid);

    void deleteAllByUser(User user);
}
