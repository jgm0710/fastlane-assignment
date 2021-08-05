package com.example.fastlaneassignment.member.service;

import com.example.fastlaneassignment.auth.exception.RefreshTokenNotValidException;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import com.example.fastlaneassignment.member.exception.LoginIdDuplicateException;
import com.example.fastlaneassignment.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Long signUp(String loginId, String password) {
        String encodingPassword = passwordEncoder.encode(password);
        Member user = Member.createUser(loginId, encodingPassword);
        return memberRepository.save(user).getId();
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    public Member getMember(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(() -> new MemberNotFoundException(loginId));
    }

    public void checkDuplicate(String loginId) {
        if (memberRepository.findByLoginId(loginId).isPresent()) {
            throw new LoginIdDuplicateException();
        }
    }

    public void withdrawal(Member member) {
        memberRepository.delete(member);
    }

    public void updatePassword(Member member, String newPassword) {
        String encodingPassword = passwordEncoder.encode(newPassword);
        member.updatePassword(encodingPassword);
    }

    public Member getMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshInfoRefreshToken(refreshToken).orElseThrow(RefreshTokenNotValidException::new);
        if (!member.validateRefreshToken()) {
            throw new RefreshTokenNotValidException();
        }
        return member;
    }
}
