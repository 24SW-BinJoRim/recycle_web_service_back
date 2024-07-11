package com.sparta.board.config;

import com.sparta.board.dto.UsedbatteryDto;
import com.sparta.board.entity.Usedbattery;
import com.sparta.board.repository.UsedbatteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvUsedbatteryWriter implements ItemWriter<UsedbatteryDto> {
    private final UsedbatteryRepository usedbatteryRepository;

    @Override
    public void write(List<? extends UsedbatteryDto> items) throws Exception {
        List<Usedbattery> usedbatteryList = new ArrayList<>();

        items.forEach(getUsedbatteryDto -> {
            Usedbattery usedbattery = getUsedbatteryDto.toEntity();
            usedbatteryList.add(usedbattery);
        });

        usedbatteryRepository.saveAll(usedbatteryList);

    }
}
