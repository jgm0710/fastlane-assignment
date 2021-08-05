package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.exception.LoginIdDuplicateException;
import com.example.fastlaneassignment.member.exception.MemberNotFoundException;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberServiceMockTest {

    MemberRepository memberRepository = mock(MemberRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    MemberService memberService = new MemberService(memberRepository, passwordEncoder);

    @Test
    @DisplayName("회원 조회 - 회원을 찾을 수 없는 경우")
    public void getMember_NotFound() {
        //given
        Long memberId = 1L;
        String loginId = "loginId";
        String refreshToken = "refreshToken";

        given(memberRepository.findById(memberId)).willReturn(Optional.empty());
        given(memberRepository.findByLoginId(loginId)).willReturn(Optional.empty());
        given(memberRepository.findByRefreshInfoRefreshToken(refreshToken)).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberNotFoundException.class, () -> memberService.getMember(memberId));
        assertThrows(MemberNotFoundException.class, () -> memberService.getMember(loginId));
        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberByRefreshToken(refreshToken));

    }

    @Test
    @DisplayName("로그인 ID 중복 체크")
    public void checkDuplicate() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("loginId")
                .build();

        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.of(member));

        //when

        //then
        assertThrows(LoginIdDuplicateException.class, () -> memberService.checkDuplicate(member.getLoginId()));

    }

}