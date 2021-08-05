package com.example.fastlaneassignment.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class SecurityJwtProperties {

    private final String JWT_TOKEN_HEADER = "X-AUTH-TOKEN";

    private String secretKey;

    private long accessTokenValidSeconds;

    private int refreshTokenValidDays;

    public long getAccessTokenValidTime() {
        return accessTokenValidSeconds * 1000;
    }

    public String  getTokenHeader() {
        return JWT_TOKEN_HEADER;
    }

    public long getRefreshTokenValidSeconds() {
        return TimeUnit.DAYS.toSeconds(refreshTokenValidDays);
    }

}
