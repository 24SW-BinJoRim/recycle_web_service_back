package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 정보 게시판
public interface InformationRepository extends JpaRepository<Information, Long> {
    List<Information> findAllByOrderByUpdatedAtDesc();

    @Query("SELECT i FROM Information i WHERE i.title LIKE %:keyword%")
    List<Information> findByKeyword(@Param("keyword") String keyword);

}
