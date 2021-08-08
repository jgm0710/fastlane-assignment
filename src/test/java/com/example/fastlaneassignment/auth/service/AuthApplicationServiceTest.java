package com.example.fastlaneassignment.auth.service;

import com.example.fastlaneassignment.auth.service.dto.SignInDto;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.common.BaseTest;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class AuthApplicationServiceTest extends BaseTest {

    @Autowired
    AuthApplicationService authApplicationService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인")
    public void signIn() {
        //given
        String password = "password";
        String encode = passwordEncoder.encode(password);

        Member user = Member.createUser("loginId", encode);
        memberRepository.save(user);

        SignInDto signInDto = new SignInDto(user.getLoginId(), password);

        //when
        TokenDto tokenDto = authApplicationService.signIn(signInDto);

        //then
        System.out.println("tokenDto = " + tokenDto);

    }
}