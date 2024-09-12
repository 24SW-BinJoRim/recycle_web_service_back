package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Information;
import com.sparta.board.entity.User;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationService {

    private final InformationRepository informationRepository;

    // 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public List<InformationResponseDto> getInformations() {

        List<Information> boardList = informationRepository.findAllByOrderByUpdatedAtDesc();
        List<InformationResponseDto> responseDtoList = new ArrayList<>();

        for (Information information : boardList) {
            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(InformationResponseDto.from(information));
        }
        return responseDtoList;
    }

    // 검색한 게시글 조회 (제목 검색)
    @Transactional(readOnly = true)
    public List<InformationResponseDto> searchInformation(BoardSearchDto boardSearchDto) {
        //String boardType = boardSearchDto.getBoardType();
        String keyword = boardSearchDto.getKeyword();
        log.info("Searching for keyword: {}", keyword);

        // 검색한 제목에 해당하는 게시글이 있는지 확인
        List<Information> boardList = informationRepository.findByKeyword(keyword);

//        if (boardList.isEmpty()) { // 해당 게시글이 없다면
//            log.warn("No results found for keyword: {}", keyword);
//            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
//        }

        List<InformationResponseDto> responseDtoList = new ArrayList<>();

        for (Information information : boardList) {
            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(InformationResponseDto.from(information));
        }

        return responseDtoList;
    }

    // 게시글 작성
    @Transactional
    public InformationResponseDto createInformation(BoardRequestsDto requestsDto, User user) {

        // 작성 글 저장
        Information information = informationRepository.save(Information.of(requestsDto, user));

        // BoardResponseDto 로 변환 후 responseEntity body 에 담아 반환
        return InformationResponseDto.from(information);

    }

    // 선택된 게시글 조회
    @Transactional(readOnly = true)
    public InformationResponseDto selectInformation(Long id) {
        Optional<Information> information = informationRepository.findById(id);
        /*
        if (board.isEmpty()) { // 해당 게시글이 없다면
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        */
        return InformationResponseDto.from(information.get());
    }
}
