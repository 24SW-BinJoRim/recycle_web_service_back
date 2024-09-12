package com.sparta.board.repository;

import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long id, User user);

    // 마이페이지 - 작성한 댓글 반환
    @Query("SELECT c.content, " +
            "COALESCE(b.title, i.title) AS postTitle, " +
            "COALESCE(b.id, i.id) AS postId " +
            "FROM Comment c " +
            "LEFT JOIN c.board b " +
            "LEFT JOIN c.information i " +
            "WHERE c.user.userid = :userid")
    List<Object[]> findByUserBoardComment(@Param("userid") Long userid);

    void deleteAllByUser(User user);
}
