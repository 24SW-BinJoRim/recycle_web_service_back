package com.sparta.board.config;

import com.sparta.board.dto.CigaretteDto;
import com.sparta.board.dto.ClothesDto;
import com.sparta.board.dto.TrashDto;
import com.sparta.board.dto.UsedbatteryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileReaderJopConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvTrashReader csvTrashReader; //추가
    private final CsvTrashWriter csvTrashWriter; //추가
    private final CsvCigaretteReader csvCigaretteReader; //추가
    private final CsvCigaretteWriter csvCigaretteWriter; //추가
    private final CsvClothesReader csvClothesReader; //추가
    private final CsvClothesWriter csvClothesWriter; //추가
    private final CsvUsedbatteryReader csvUsedbatteryReader; //추가
    private final CsvUsedbatteryWriter csvUsedbatteryWriter; //추가

    private static final int chunkSize = 500; //데이터 처리할 row size

    // 쓰레기통 정보 저장 Job, Job은 여러 Step을 가질 수 있음.
    @Bean
    public Job csvTrashJob(){
        return jobBuilderFactory.get("csvTrashJob")
                .start(csvTrashReaderStep())
                .build();
    }
    // csv 파일 읽고 DB에 쓰는 Step
    @Bean
    public Step csvTrashReaderStep(){
        return stepBuilderFactory.get("csvTrashReaderStep")
                //<reader에 넘겨줄 타입, writer에 넙겨줄 타입>
                .<TrashDto, TrashDto>chunk(chunkSize)
                .reader(csvTrashReader.csvTrashReader())
                .writer(csvTrashWriter)
                .allowStartIfComplete(true)
                .build();
    }

    // 담배꽁초 정보 저장
    @Bean
    public Job csvCigaretteJob(){
        return jobBuilderFactory.get("csvCigaretteJob")
                .start(csvCigaretteReaderStep())
                .build();
    }
    @Bean
    public Step csvCigaretteReaderStep(){
        return stepBuilderFactory.get("csvCigaretteReaderStep")
                //<reader에 넘겨줄 타입, writer에 넙겨줄 타입>
                .<CigaretteDto, CigaretteDto>chunk(chunkSize)
                .reader(csvCigaretteReader.csvCigaretteReader())
                .writer(csvCigaretteWriter)
                .allowStartIfComplete(true)
                .build();
    }

    // 의류수거함 정보 저장
    @Bean
    public Job csvClothesJob(){
        return jobBuilderFactory.get("csvClothesJob")
                .start(csvClothesReaderStep())
                .build();
    }
    @Bean
    public Step csvClothesReaderStep(){
        return stepBuilderFactory.get("csvClothesReaderStep")
                //<reader에 넘겨줄 타입, writer에 넙겨줄 타입>
                .<ClothesDto, ClothesDto>chunk(chunkSize)
                .reader(csvClothesReader.csvClothesReader())
                .writer(csvClothesWriter)
                .allowStartIfComplete(true)
                .build();
    }

    // 폐형광등(폐건전지) 정보 저장
    @Bean
    public Job csvUsedbatteryJob(){
        return jobBuilderFactory.get("csvUsedbatteryJob")
                .start(csvUsedbatteryReaderStep())
                .build();
    }
    @Bean
    public Step csvUsedbatteryReaderStep(){
        return stepBuilderFactory.get("csvUsedbatteryReaderStep")
                //<reader에 넘겨줄 타입, writer에 넙겨줄 타입>
                .<UsedbatteryDto, UsedbatteryDto>chunk(chunkSize)
                .reader(csvUsedbatteryReader.csvUsedbatteryReader())
                .writer(csvUsedbatteryWriter)
                .allowStartIfComplete(true)
                .build();
    }
}
