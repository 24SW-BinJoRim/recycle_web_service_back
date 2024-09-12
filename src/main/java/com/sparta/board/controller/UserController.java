package com.sparta.board.controller;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.SuccessResponse;
import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eoditsseu/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }

    @GetMapping("/auth")
    public ResponseEntity<?> authUser(@CookieValue(value = "x_auth", defaultValue = "") String token) {
        return ResponseEntity.ok(Map.of(
                "_id", "",
                "isAdmin", true,
                "isAuth", false
        ));
    }
}
