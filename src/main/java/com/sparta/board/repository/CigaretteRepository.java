package com.sparta.board.repository;

import com.sparta.board.entity.Cigarette;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CigaretteRepository extends JpaRepository<Cigarette, Long> {
}
