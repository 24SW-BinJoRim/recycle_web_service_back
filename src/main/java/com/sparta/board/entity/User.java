package com.sparta.board.entity;

import com.sparta.board.entity.enumSet.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String username, String nickname, String password, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public static User of(String username, String nickname, String password, UserRoleEnum role) {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
    }
}
