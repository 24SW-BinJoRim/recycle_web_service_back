
package com.sparta.board.config;

import com.sparta.board.dto.TrashDto;
import com.sparta.board.entity.Trash;
import com.sparta.board.repository.TrashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvTrashWriter implements ItemWriter<TrashDto> {
    private final TrashRepository trashRepository;

    @Override
    public void write(List<? extends TrashDto> items) throws Exception {
        List<Trash> trashList = new ArrayList<>();

        items.forEach(getTrashDto -> {
            Trash trash = getTrashDto.toEntity();
            trashList.add(trash);
        });

        trashRepository.saveAll(trashList);

    }
}
