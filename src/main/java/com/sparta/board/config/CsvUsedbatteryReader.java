package com.sparta.board.config;

import com.sparta.board.dto.UsedbatteryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class CsvUsedbatteryReader {
    public ItemReader<? extends UsedbatteryDto> csvUsedbatteryReader(){

        // 파일읽기
        FlatFileItemReader<UsedbatteryDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/서울특별시 동작구 폐형광등(폐건전지) 현황.csv")); //읽을 파일 경로 지정
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setEncoding("UTF-8"); //인코딩 설정
        // 데이터 내부에 개행이 있으면 추가
        //flatFileItemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

        // defaultLineMapper: 읽으려는 데이터 LineMapper을 통해 Dto로 매핑
        DefaultLineMapper<UsedbatteryDto> defaultLineMapper = new DefaultLineMapper<>();

        // delimitedLineTokenizer : csv 파일에서 구분자 지정하고 구분한 데이터 setNames를 통해 각 이름 설정
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(","); //csv 파일에서 구분자
        delimitedLineTokenizer.setNames("address", "lat", "lng", "title", "detail", "type"); //행으로 읽은 데이터 매칭할 데이터 각 이름
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer); //lineTokenizer 설정

        // beanWrapperFieldSetMapper: 매칭할 class 타입 지정
        BeanWrapperFieldSetMapper<UsedbatteryDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(UsedbatteryDto.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper); //fieldSetMapper 지정

        flatFileItemReader.setLineMapper(defaultLineMapper); //lineMapper 지정

        return flatFileItemReader;
    }
}
