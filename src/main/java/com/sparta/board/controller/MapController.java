package com.sparta.board.controller;

import com.sparta.board.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

public class MapController {

    private final MapService mapService;

    @ResponseBody
    @GetMapping("/eoditsseu/api/maps")
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


}

