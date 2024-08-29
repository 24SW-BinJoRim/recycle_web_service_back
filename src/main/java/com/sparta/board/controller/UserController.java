package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.SuccessResponse;
import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponseDto<SuccessResponse> register(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public ApiResponseDto<SuccessResponse> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }

    /*
    @PostMapping("/signout")
    public ApiResponseDto<SuccessResponse> signout(@RequestBody LoginRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.signout(requestDto, userDetails.getUser());
    }
    */
}
