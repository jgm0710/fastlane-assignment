package com.example.fastlaneassignment.auth.service;

import com.example.fastlaneassignment.auth.JwtTokenProvider;
import com.example.fastlaneassignment.auth.exception.ManipulateOtherMembersResourceException;
import com.example.fastlaneassignment.auth.exception.PasswordMismatchException;
import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import com.example.fastlaneassignment.config.SecurityJwtProperties;
import com.example.fastlaneassignment.member.domain.Member;
import com.example.fastlaneassignment.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SecurityJwtProperties jwtProperties;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    public void checkPermission(Long currentUserId, Long memberId) {
        if (!currentUserId.equals(memberId)) {
            throw new ManipulateOtherMembersResourceException();
        }
    }

    public void verifyPassword(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    public TokenDto initAuthToken(Member member) {
        String accessToken = jwtTokenProvider.createToken(member.getId(), member.getLoginId(), member.getRoleNames());
        String refreshToken = initRefreshToken(member);

        return TokenDto.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .accessTokenValidSeconds(jwtProperties.getAccessTokenValidSeconds())
                .refreshToken(refreshToken)
                .refreshTokenValidSeconds(jwtProperties.getRefreshTokenValidSeconds())
                .build();
    }

    private String initRefreshToken(Member member) {
        while (true) {
            String refreshToken= member.initRefreshToken(jwtProperties.getRefreshTokenValidDays());
            if (memberRepository.findByRefreshInfoRefreshToken(refreshToken).isEmpty()) {
                return refreshToken;
            }
        }
    }
}
