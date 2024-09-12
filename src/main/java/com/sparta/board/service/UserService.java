package com.sparta.board.service;

import com.sparta.board.common.ApiResponseDto;
import com.sparta.board.common.ResponseUtils;
import com.sparta.board.common.SuccessResponse;
import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.entity.User;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.entity.enumSet.UserRoleEnum;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.LikesRepository;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public ResponseEntity<?> register(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        //String password = requestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            log.info("중복된 회원입니다.");
            ResponseEntity.ok(Map.of("token", false));
        }

        // 입력한 username, password, admin 으로 user 객체 만들어 repository 에 저장
        UserRoleEnum role = requestDto.getAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
        userRepository.save(User.of(username, nickname, password, role));
        log.info("회원가입 성공 !");

        return ResponseEntity.ok(Map.of("token", true));
    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인 & 비밀번호 확인
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            log.info("존재하지 않는 회원입니다.");
            ResponseEntity.ok(Map.of("token", false));
        }

        // header 에 들어갈 JWT 세팅
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));

        return ResponseEntity.ok(Map.of(
                "token", true,
                "user", Map.of(
                        "userid", user.get().getUserid(),
                        "username", user.get().getUsername(),
                        "nickname", user.get().getNickname(),
                        "password", user.get().getPassword()
                )
        ));

    }

    /*
    // 회원 탈퇴
    @Transactional
    public ApiResponseDto<SuccessResponse> signout(LoginRequestDto requestDto, User user) {

        // 비밀번호 확인
        String password = requestDto.getPassword();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestApiException(ErrorType.NOT_MATCHING_PASSWORD);
        }

        boardRepository.deleteAllByUser(user);
        commentRepository.deleteAllByUser(user);
        likesRepository.deleteAllByUser(user);
        userRepository.delete(user);

        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "회원탈퇴 완료"));
    }
    */
}
