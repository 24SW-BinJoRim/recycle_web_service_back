package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.BoardSearchDto;
import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.service.MypageService;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/userpage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/posts")
    public List<Map<String, Object>> searchUserPosts(@RequestParam String userid,
                                                    @RequestParam String nickname) {
        // DTO에 검색 조건 세팅
        UserRequestDto userDTO = new UserRequestDto();
        userDTO.setUserid(userid);
        userDTO.setNickname(nickname);

        // Service 호출
        return mypageService.searchUserPost(userDTO);
    }

    @GetMapping("/comments")
    public List<Map<String, Object>> searchUserComments(@RequestParam String userid,
                                                    @RequestParam String nickname) {
        // DTO에 검색 조건 세팅
        UserRequestDto userDTO = new UserRequestDto();
        userDTO.setUserid(userid);
        userDTO.setNickname(nickname);

        // Service 호출
        return mypageService.searchUserPost(userDTO);
    }

    /*
    @GetMapping("/likes")

     */
}
