package com.sparta.board.repository;

import com.sparta.board.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {
}
