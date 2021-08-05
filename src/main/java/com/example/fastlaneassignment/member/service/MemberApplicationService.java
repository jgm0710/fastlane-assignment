package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.auth.service.AuthService;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.service.dto.PasswordUpdateDto;
import com.example.fastlaneassignment.member.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberApplicationService {

    private final AuthService authService;

    private final MemberService memberService;

    @Transactional
    public Long signUp(SignUpDto signUpDto) {
        memberService.checkDuplicate(signUpDto.getLoginId());
        return memberService.signUp(signUpDto.getLoginId(), signUpDto.getPassword());
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberService.getMember(memberId);
    }

    @Transactional
    public void updatePassword(Long currentUserId, Long memberId, PasswordUpdateDto passwordUpdateDto) {
        authService.checkPermission(currentUserId, memberId);
        Member member = memberService.getMember(memberId);
        authService.verifyPassword(member, passwordUpdateDto.getOldPassword());
        memberService.updatePassword(member, passwordUpdateDto.getNewPassword());
    }

    @Transactional
    public void withdrawal(Long currentUserId, Long memberId) {
        authService.checkPermission(currentUserId, memberId);
        Member member = memberService.getMember(memberId);
        memberService.withdrawal(member);
    }
}
