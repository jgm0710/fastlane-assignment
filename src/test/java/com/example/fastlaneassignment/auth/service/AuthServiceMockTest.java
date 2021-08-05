package com.example.fastlaneassignment.auth.service;

import com.example.fastlaneassignment.auth.JwtTokenProvider;
import com.example.fastlaneassignment.auth.exception.ManipulateOtherMembersResourceException;
import com.example.fastlaneassignment.auth.exception.PasswordMismatchException;
import com.example.fastlaneassignment.config.SecurityJwtProperties;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AuthServiceMockTest {

    SecurityJwtProperties jwtProperties = mock(SecurityJwtProperties.class);
    JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    MemberRepository memberRepository = mock(MemberRepository.class);

    AuthService authService = new AuthService(
            jwtProperties,
            jwtTokenProvider,
            passwordEncoder,
            memberRepository
    );

    @Test
    @DisplayName("리소스 조작 권한 확인 - 다른 회원의 리소스를 조작하는 경우")
    public void checkPermission_ManipulateOtherMembersResource() {
        //given

        //when

        //then
        assertThrows(ManipulateOtherMembersResourceException.class, () -> authService.checkPermission(1L, 2L));

    }

    @Test
    @DisplayName("비밀번호 검증 - 비밀번호가 틀렸을 경우")
    public void verifyPassword_WrongPassword() {
        //given
        PasswordEncoder localPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String oldPassword = localPasswordEncoder.encode("oldPassword");

        Member user = Member.builder()
                .id(1L)
                .loginId("loginId")
                .password(oldPassword)
                .build();

        //when

        //then
        assertThrows(PasswordMismatchException.class, () -> authService.verifyPassword(user, "newPassword"));

    }

}