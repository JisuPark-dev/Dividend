package com.zerobase.dividends.model;

import lombok.Data;

import java.util.List;

public class Auth {
    @Data // 로그인
    public static class SignIn {
        private String username;
        private String password;

    }

    @Data // 회원가입
    public static class SignUp {
        private String username;
        private String password;
        private List<String> roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                        .username(this.username)
                        .password(this.password)
                        .roles(this.roles)
                        .build();
        }
    }
}
