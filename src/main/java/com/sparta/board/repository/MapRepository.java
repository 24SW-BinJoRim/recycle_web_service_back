package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {

    @Query("SELECT m.lat, m.lng, m.title, m.detail, m.type FROM Map m")
    List<Object[]> findAllMapDetails();

    @Query("select m.lat, m.lng, m.title, m.detail, m.type from Map m where m.address LIKE %:keyword%")
    List<Object[]> findByMapSearchDetails(@Param("keyword") String keyword);


}
