package com.example.fastlaneassignment.auth.api.dto;

import com.example.fastlaneassignment.auth.service.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

    private Long memberId;

    private String accessToken;

    private long accessTokenValidSeconds;

    private String refreshToken;

    private long refreshTokenValidSeconds;

    public static TokenResponseDto of(TokenDto tokenDto) {
        return TokenResponseDto.builder()
                .memberId(tokenDto.getMemberId())
                .accessToken(tokenDto.getAccessToken())
                .accessTokenValidSeconds(tokenDto.getAccessTokenValidSeconds())
                .refreshToken(tokenDto.getRefreshToken())
                .refreshTokenValidSeconds(tokenDto.getRefreshTokenValidSeconds())
                .build();
    }
}
