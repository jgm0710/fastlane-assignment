package com.example.fastlaneassignment.auth.service.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
public class TokenDto {

    private Long memberId;

    private String accessToken;

    private long accessTokenValidSeconds;

    private String refreshToken;

    private long refreshTokenValidSeconds;

    @Builder
    public TokenDto(Long memberId, String accessToken, long accessTokenValidSeconds, String refreshToken, long refreshTokenValidSeconds) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.accessTokenValidSeconds = accessTokenValidSeconds;
        this.refreshToken = refreshToken;
        this.refreshTokenValidSeconds = refreshTokenValidSeconds;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getAccessTokenValidSeconds() {
        return accessTokenValidSeconds;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getRefreshTokenValidSeconds() {
        return refreshTokenValidSeconds;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "memberId=" + memberId +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenValidSeconds=" + accessTokenValidSeconds +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenValidSeconds=" + refreshTokenValidSeconds +
                '}';
    }
}
