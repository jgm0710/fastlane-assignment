package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.common.BaseTest;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.domain.MemberRole;
import com.example.fastlaneassignment.member.domain.RefreshInfo;
import com.example.fastlaneassignment.member.exception.MemberNotFoundException;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends BaseTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입")
    public void signUp() {
        //given
        String loginId = "loginId";
        String password = "password";

        //when

        Long memberId = memberService.signUp(loginId, password);

        //then
        Member findMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        assertEquals(loginId, findMember.getLoginId());
        assertTrue(passwordEncoder.matches(password, findMember.getPassword()));
        assertTrue(findMember.getRoleNames().contains(MemberRole.USER.name()),"회원 권한은 USER 이다.");
    }

    @Test
    @DisplayName("refresh token 을 통한 회원 조회")
    public void getMemberByRefreshToken() {
        //given
        RefreshInfo refreshInfo = RefreshInfo.init(15);

        Member member = Member.builder()
                .loginId("loginId")
                .password("password")
                .refreshInfo(refreshInfo)
                .build();

        Member savedMember = memberRepository.save(member);

        RefreshInfo refreshInfo2 = RefreshInfo.init(15);
        Member member2 = Member.builder()
                .loginId("loginId2")
                .password("password2")
                .refreshInfo(refreshInfo2)
                .build();
        memberRepository.save(member2);

        //when
        Member findMember = memberService.getMemberByRefreshToken(refreshInfo.getRefreshToken());

        //then
        assertEquals(findMember.getId(), savedMember.getId());

    }

    @Test
    @DisplayName("회원 탈퇴")
    public void withdrawal() {
        //given
        Member user = Member.createUser("loginId", "password");
        memberRepository.save(user);

        //when
        memberService.withdrawal(user);

        //then
        assertTrue(memberRepository.findById(user.getId()).isEmpty());
    }

    @Test
    @DisplayName("비밀번호 변경")
    public void updatePassword() {
        //given
        Member user = Member.createUser("loginIe", "oldPassword");

        //when
        String newPassword = "newPassword";
        memberService.updatePassword(user, newPassword);

        //then
        assertTrue(passwordEncoder.matches(newPassword, user.getPassword()));

    }

}