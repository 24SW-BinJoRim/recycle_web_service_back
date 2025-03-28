package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.sparta.board.dto.MapRequestDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sparta.board.dto.MapResponseDto.from;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/maps")
public class MapController {

    private final MapService mapService;

    @ResponseBody
    @GetMapping
    public List<Map<String, Object>> getAllMapDetails() {
        List<Object[]> results = mapService.getAllMapDetails();
        List<Map<String, Object>> trashs = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> trashMap = new HashMap<>();
            trashMap.put("lat", result[0]);
            trashMap.put("lng", result[1]);
            trashMap.put("title", result[2]);
            trashMap.put("detail", result[3]);
            trashMap.put("type", result[4]);
            trashs.add(trashMap);
        }
        return trashs;
    }

    // 검색된 맵 조회 (이름 검색)
    @GetMapping("/search")
    public List<MapResponseDto> searchMap(@RequestParam String keyword) {
        MapRequestDto requestDto = new MapRequestDto();
        requestDto.setKeyword(keyword);
        return mapService.searchMap(requestDto);
    }
}

