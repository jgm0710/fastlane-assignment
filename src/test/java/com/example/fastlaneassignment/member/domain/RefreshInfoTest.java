package com.example.fastlaneassignment.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RefreshInfoTest {

    @Test
    @DisplayName("refresh token 유효성 확인")
    public void isValid() {
        //given
        RefreshInfo refreshInfo = RefreshInfo.builder()
                .refreshToken("token")
                .refreshTokenValidDate(LocalDateTime.now().minusDays(1))
                .build();

        //when
        boolean valid = refreshInfo.isValid();

        //then
        assertFalse(valid);

    }

}