package com.example.fastlaneassignment.auth.service;

import com.example.fastlaneassignment.auth.service.dto.SignInDto;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.domain.RefreshInfo;
import com.example.fastlaneassignment.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class AuthApplicationServiceMockTest {

    MemberService memberService = mock(MemberService.class);
    AuthService authService = mock(AuthService.class);

    AuthApplicationService authApplicationService = new AuthApplicationService(
            memberService,
            authService
    );

    @Test
    @DisplayName("로그인")
    public void signIn() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("loginId")
                .password("password")
                .build();

        SignInDto signInDto = new SignInDto(member.getLoginId(), member.getPassword());

        given(memberService.getMember(member.getLoginId())).willReturn(member);

        //when
        TokenDto tokenDto = authApplicationService.signIn(signInDto);

        //then
        then(memberService).should(times(1)).getMember(member.getLoginId());
        then(authService).should(times(1)).verifyPassword(member, signInDto.getPassword());
        then(authService).should(times(1)).initAuthToken(member);

    }

    @Test
    @DisplayName("로그아웃")
    public void signOut() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("loginId")
                .password("password")
                .build();

        given(memberService.getMember(member.getId())).willReturn(member);

        //when
        authApplicationService.signOut(member.getId());

        //then
        then(memberService).should(times(1)).getMember(member.getId());

        assertNull(member.getRefreshInfo());

    }

    @Test
    @DisplayName("인증 토큰 재발급")
    public void refresh() {
        //given
        RefreshInfo refreshInfo = RefreshInfo.init(19);
        Member member = Member.builder()
                .id(1L)
                .loginId("loginId")
                .password("password")
                .refreshInfo(refreshInfo)
                .build();

        given(memberService.getMemberByRefreshToken(refreshInfo.getRefreshToken())).willReturn(member);

        //when
        authApplicationService.refresh(refreshInfo.getRefreshToken());

        //then
        then(memberService).should(times(1)).getMemberByRefreshToken(refreshInfo.getRefreshToken());
        then(authService).should(times(1)).initAuthToken(member);

    }

}