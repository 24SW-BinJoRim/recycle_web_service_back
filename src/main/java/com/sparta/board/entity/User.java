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
    private Long id;

    @Column(nullable = false, length = 20)
    private String userid;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String userid, String nickname, String password, UserRoleEnum role) {
        this.userid = userid;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public static User of(String userid, String nickname, String password, UserRoleEnum role) {
        return User.builder()
                .userid(userid)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
    }
}
