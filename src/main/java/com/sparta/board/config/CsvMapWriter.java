
package com.sparta.board.config;

import com.sparta.board.dto.MapDto;
import com.sparta.board.entity.Map;
import com.sparta.board.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvMapWriter implements ItemWriter<MapDto> {
    private final MapRepository mapRepository;

    @Override
    public void write(List<? extends MapDto> items) throws Exception {
        List<Map> mapList = new ArrayList<>();

        items.forEach(getMapDto -> {
            Map map = getMapDto.toEntity();
            mapList.add(map);
        });

        mapRepository.saveAll(mapList);

    }
}
