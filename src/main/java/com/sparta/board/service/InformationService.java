package com.sparta.board.service;

import com.sparta.board.entity.Information;
import com.sparta.board.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;

    public List<Information> getAllBoards() {
        return informationRepository.findAll();
    }

    public Information getBoard(Long id) {
        return informationRepository.findById(id).orElse(null);
    }

    public Information saveBoard(Information information) {
        return informationRepository.save(information);
    }

    public void deleteBoard(Long id) {
        informationRepository.deleteById(id);
    }
}
