package com.sparta.board.config;

import com.sparta.board.dto.CigaretteDto;
import com.sparta.board.dto.ClothesDto;
import com.sparta.board.entity.Clothes;
import com.sparta.board.repository.ClothesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvClothesWriter implements ItemWriter<ClothesDto> {
    private final ClothesRepository clothesRepository;

    @Override
    public void write(List<? extends ClothesDto> items) throws Exception {
        List<Clothes> clothesList = new ArrayList<>();

        items.forEach(getClothesDto -> {
            Clothes clothes = getClothesDto.toEntity();
            clothesList.add(clothes);
        });

        clothesRepository.saveAll(clothesList);

    }
}
