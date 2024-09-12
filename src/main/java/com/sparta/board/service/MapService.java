package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.repository.MapRepository;
import com.sparta.board.dto.MapRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final MapRepository mapRepository;

    // 맵 데이터 전체 조회
    public List<Object[]> getAllMapDetails(){
        return mapRepository.findAllMapDetails();
    }

    // 검색한 게시글 조회 (제목 검색)
    @Transactional(readOnly = true)
    public List<MapResponseDto> searchMap(MapRequestDto requestDto) {

        String keyword = requestDto.getKeyword();
        log.info("Searching for keyword: {}", keyword);

        // 검색한 제목에 해당하는 게시글이 있는지 확인
        List<Object[]> mapList = mapRepository.findByMapSearchDetails(keyword);
        if (mapList.isEmpty()) { // 해당 게시글이 없다면
            log.warn("No results found for keyword: {}", keyword);
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        // 중복 제거를 위한 Set 사용
        Set<MapDto> mapDtoSet = new HashSet<>();
        for (Object[] result : mapList) {
            MapDto mapDto = new MapDto();
            mapDto.setLat((String) result[0]);
            mapDto.setLng((String) result[1]);
            mapDto.setTitle((String) result[2]);
            mapDto.setDetail((String) result[3]);
            mapDto.setType((String) result[4]);
            mapDtoSet.add(mapDto);
        }

        List<MapResponseDto> responseDtoList = new ArrayList<>();

        for (MapDto mapDto : mapDtoSet) {
            MapResponseDto responseDto = MapResponseDto.from(mapDto);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

}
