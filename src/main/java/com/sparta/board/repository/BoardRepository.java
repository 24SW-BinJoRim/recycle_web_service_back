package com.sparta.board.repository;

import com.sparta.board.dto.BoardSearchDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Information;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByUpdatedAtDesc();
    Optional<Board> findById(Long id);
    Optional<Board> findByIdAndUser(Long id, User user);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword%")
    List<Board> findByKeyword(@Param("keyword") String keyword);

    // 마이페이지 - 중고거래 게시판 글 반환
    @Query("SELECT b.title, b.contents, b.id FROM Board b WHERE b.user.userid = :userid")
    List<Object[]> findByUserPost(@Param("userid") Long userid);


    void deleteAllByUser(User user);
}
