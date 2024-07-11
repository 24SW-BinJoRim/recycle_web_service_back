package com.sparta.board.config;

import com.sparta.board.dto.CigaretteDto;
import com.sparta.board.entity.Cigarette;
import com.sparta.board.repository.CigaretteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvCigaretteWriter implements ItemWriter<CigaretteDto> {
    private final CigaretteRepository cigaretteRepository;

    @Override
    public void write(List<? extends CigaretteDto> items) throws Exception {
        List<Cigarette> cigaretteList = new ArrayList<>();

        items.forEach(getCigaretteDto -> {
            Cigarette cigarette = getCigaretteDto.toEntity();
            cigaretteList.add(cigarette);
        });

        cigaretteRepository.saveAll(cigaretteList);

        }
}
