package com.sparta.board.service;

import com.sparta.board.dto.MypageRequestDto;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.LikesRepository;
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
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    // 해당 유저가 작성한 게시글 조회
    @Transactional(readOnly = true)
    public List<Map<String, Object>> searchUserPost(MypageRequestDto mypageRequestDto) {
        Long userid = mypageRequestDto.getUserid();
        String username = mypageRequestDto.getUsername();

        // 해당 유저가 작성한 중고거래 게시판의 게시글이 있는지 확인
        // 정보 게시판은 유저가 작성할 수 없으므로 제외
        List<Object[]> results = boardRepository.findByUserPost(userid);
        List<Map<String, Object>> info = new ArrayList<>();

        int i = 0;
        for (Object[] result : results) {
            Map<String, Object> trashMap = new HashMap<>();
            trashMap.put("id", i);
            trashMap.put("title", result[0]);
            trashMap.put("contents", result[1]);
            trashMap.put("postId", result[2]);
            trashMap.put("type", "used-transaction");
            info.add(trashMap);
            i++;
        }
        return info;
        }

    // 해당 유저가 작성한 댓글 조회
    @Transactional(readOnly = true)
    public List<Map<String, Object>> searchUserComment(MypageRequestDto mypageRequestDto) {
        Long userid = mypageRequestDto.getUserid();
        String username = mypageRequestDto.getUsername();

        // 중고거래 게시판 & 정보게시판에 작성한 댓글
        List<Object[]> boardCommentresults = commentRepository.findByUserBoardComment(userid);
        List<Map<String, Object>> info = new ArrayList<>();

        int i = 0;
        for (Object[] bc : boardCommentresults) {
            Map<String, Object> trashMap = new HashMap<>();
            trashMap.put("id", i);
            trashMap.put("content", bc[0]);
            trashMap.put("postTitle", bc[1]);
            trashMap.put("postId", bc[2]);
            info.add(trashMap);
            i++;
        }

        return info;
    }

    // 해당 유저가 등록한 좋아요 조회
    @Transactional(readOnly = true)
    public List<Map<String, Object>> searchUserLike(MypageRequestDto mypageRequestDto) {
        Long userid = mypageRequestDto.getUserid();
        String username = mypageRequestDto.getUsername();

        // 중고거래 게시판 & 정보게시판에 누른 좋아요
        List<Object[]> boardLikeResults = likesRepository.findByUserBoardLike(userid);
        List<Object[]> informationLikeResults = likesRepository.findByUserInfoLike(userid);
        List<Map<String, Object>> info = new ArrayList<>();

        int i = 0;
        for (Object[] bl : boardLikeResults) {
            Map<String, Object> trashMap = new HashMap<>();
            trashMap.put("id", i);
            trashMap.put("title", bl[0]);
            trashMap.put("content", bl[1]);
            trashMap.put("postId", bl[2]);
            trashMap.put("type", "used-transaction");
            info.add(trashMap);
            i++;
        }

        for (Object[] il : informationLikeResults) {
            Map<String, Object> trashMap = new HashMap<>();
            trashMap.put("id", i);
            trashMap.put("title", il[0]);
            trashMap.put("content", il[1]);
            trashMap.put("postId", il[2]);
            trashMap.put("type", "information");
            info.add(trashMap);
            i++;
        }
        return info;
    }
}