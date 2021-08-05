package com.example.fastlaneassignment.auth.service;

import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.common.BaseTest;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest extends BaseTest {

    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("인증 토큰 생성")
    public void initAuthToken() {
        //given
        Member user = Member.createUser("loginId", "password");
        memberRepository.save(user);

        //when
        TokenDto tokenDto = authService.initAuthToken(user);

        //then
        System.out.println("tokenDto = " + tokenDto);

    }
}