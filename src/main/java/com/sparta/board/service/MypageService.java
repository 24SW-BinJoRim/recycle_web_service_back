package com.sparta.board.service;

import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 해당 유저가 작성한 게시글 조회
    @Transactional(readOnly = true)
    public List<Map<String, Object>> searchUserPost(UserRequestDto userRequestDto) {
        String userid = userRequestDto.getUserid();
        String nickname = userRequestDto.getNickname();
        log.info("Searching for userid: {}", userid);

        // 검색한 제목에 해당하는 중고거래 게시판의 게시글이 있는지 확인
        List<Object[]> results = boardRepository.findByUserPost(userid);
        List<Map<String, Object>> trashs = new ArrayList<>();

        int i = 0;
        for (Object[] result : results) {
            Map<String, Object> trashMap = new HashMap<>();
            trashMap.put("id", i);
            trashMap.put("title", result[0]);
            trashMap.put("contents", result[1]);
            trashMap.put("postId", result[2]);
            trashMap.put("type", "used-transaction");
            trashs.add(trashMap);
            i++;
        }
        return trashs;
        }
}