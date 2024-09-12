package com.sparta.board.controller;

import com.sparta.board.dto.MypageRequestDto;
import com.sparta.board.service.MypageService;
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
    public List<Map<String, Object>> searchUserPost(@RequestParam Long userid, @RequestParam String username) {

        MypageRequestDto requestDto = new MypageRequestDto();
        requestDto.setUserid(userid);
        requestDto.setUsername(username);

        return mypageService.searchUserPost(requestDto);
    }

    @GetMapping("/comments")
    public List<Map<String, Object>> searchUserComment(@RequestParam Long userid, @RequestParam String username) {

        MypageRequestDto requestDto = new MypageRequestDto();
        requestDto.setUserid(userid);
        requestDto.setUsername(username);

        return mypageService.searchUserComment(requestDto);
    }

    @GetMapping("/likes")
    public List<Map<String, Object>> searchUserLike(@RequestParam Long userid, @RequestParam String username) {

        MypageRequestDto requestDto = new MypageRequestDto();
        requestDto.setUserid(userid);
        requestDto.setUsername(username);

        return mypageService.searchUserLike(requestDto);
    }

}
