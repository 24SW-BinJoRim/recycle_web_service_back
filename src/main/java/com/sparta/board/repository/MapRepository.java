package com.sparta.board.repository;

import com.sparta.board.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {

    @Query("SELECT m.lat, m.lng, m.title, m.detail, m.type FROM Map m")
    List<Object[]> findAllMapDetails();

}
