package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Trash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrashRepository extends JpaRepository<Trash, Long> {
    //List<Trash> getTrashList();
}
