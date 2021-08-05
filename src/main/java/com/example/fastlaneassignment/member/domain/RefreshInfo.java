package com.example.fastlaneassignment.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshInfo {

    @Column(length = 1000)
    private String refreshToken;

    private LocalDateTime refreshTokenValidDate;

    @Builder
    public RefreshInfo(String refreshToken, LocalDateTime refreshTokenValidDate) {
        this.refreshToken = refreshToken;
        this.refreshTokenValidDate = refreshTokenValidDate;
    }


    public static RefreshInfo init(int refreshTokenValidDays) {
        return RefreshInfo.builder()
                .refreshToken(UUID.randomUUID().toString())
                .refreshTokenValidDate(LocalDateTime.now().plusDays(refreshTokenValidDays))
                .build();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(refreshTokenValidDate);
    }
}
