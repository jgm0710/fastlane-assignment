package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.auth.service.AuthService;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.service.dto.PasswordUpdateDto;
import com.example.fastlaneassignment.member.service.dto.SignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class MemberApplicationServiceMockTest {

    AuthService authService = mock(AuthService.class);
    MemberService memberService = mock(MemberService.class);

    MemberApplicationService memberApplicationService = new MemberApplicationService(
            authService,
            memberService
    );

    @Test
    @DisplayName("회원 가입")
    public void signUp() {
        //given
        SignUpDto signUpDto = new SignUpDto("loginId", "password");

        //when
        Long memberId = memberApplicationService.signUp(signUpDto);

        //then
        then(memberService).should(times(1)).checkDuplicate(signUpDto.getLoginId());
        then(memberService).should(times(1)).signUp(signUpDto.getLoginId(), signUpDto.getPassword());
    }

    @Test
    @DisplayName("비밀번호 변경")
    public void updatePassword() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("loginId")
                .password("oldPassword")
                .build();

        PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto(member.getPassword(), "newPassword");

        given(memberService.getMember(member.getId())).willReturn(member);

        //when
        memberApplicationService.updatePassword(member.getId(), member.getId(), passwordUpdateDto);

        //then
        then(authService).should(times(1)).checkPermission(member.getId(), member.getId());
        then(memberService).should(times(1)).getMember(member.getId());
        then(authService).should(times(1)).verifyPassword(member, passwordUpdateDto.getOldPassword());
        then(memberService).should(times(1)).updatePassword(member, passwordUpdateDto.getNewPassword());

    }

    @Test
    @DisplayName("회원 탈퇴")
    public void withdrawal() {
        //given
        Member member = Member.builder()
                .id(1L)
                .loginId("loginId")
                .password("password")
                .build();

        given(memberService.getMember(member.getId())).willReturn(member);

        //when
        memberApplicationService.withdrawal(member.getId(),member.getId());

        //then
        then(authService).should(times(1)).checkPermission(member.getId(), member.getId());
        then(memberService).should(times(1)).getMember(member.getId());
        then(memberService).should(times(1)).withdrawal(member);
    }

}