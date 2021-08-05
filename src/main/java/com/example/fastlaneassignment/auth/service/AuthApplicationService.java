package com.example.fastlaneassignment.auth.service;

import com.example.fastlaneassignment.auth.service.dto.SignInDto;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final MemberService memberService;

    private final AuthService authService;

    @Transactional
    public TokenDto signIn(SignInDto signInDto) {
        Member member = memberService.getMember(signInDto.getLoginId());
        authService.verifyPassword(member, signInDto.getPassword());

        return authService.initAuthToken(member);
    }

    @Transactional
    public void signOut(Long memberId) {
        Member member = memberService.getMember(memberId);
        member.expireRefreshToken();
    }

    @Transactional
    public TokenDto refresh(String refreshToken) {
        Member member = memberService.getMemberByRefreshToken(refreshToken);
        return authService.initAuthToken(member);
    }
}
