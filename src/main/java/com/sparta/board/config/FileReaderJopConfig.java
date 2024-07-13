package com.sparta.board.config;
/*
import com.sparta.board.dto.MapDto;
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
    private final CsvMapReader csvMapReader; //추가
    private final CsvMapWriter csvMapWriter; //추가

    private static final int chunkSize = 2000; //데이터 처리할 row size

    // 쓰레기통 정보 저장 Job, Job은 여러 Step을 가질 수 있음.
    @Bean
    public Job csvMapJob(){
        return jobBuilderFactory.get("csvMapJob")
                .start(csvMapReaderStep())
                .build();
    }
    // csv 파일 읽고 DB에 쓰는 Step
    @Bean
    public Step csvMapReaderStep(){
        return stepBuilderFactory.get("csvMapReaderStep")
                //<reader에 넘겨줄 타입, writer에 넙겨줄 타입>
                .<MapDto, MapDto>chunk(chunkSize)
                .reader(csvMapReader.csvMapReader())
                .writer(csvMapWriter)
                .allowStartIfComplete(true)
                .build();
    }
}

 */
