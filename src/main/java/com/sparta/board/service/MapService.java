package com.sparta.board.service;

import com.sparta.board.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;

    // 맵 데이터 전체 조회
    public List<Object[]> getAllMapDetails(){
        return mapRepository.findAllMapDetails();
    }

}
