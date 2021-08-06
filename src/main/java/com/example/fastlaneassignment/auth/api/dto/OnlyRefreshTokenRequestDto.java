package com.example.fastlaneassignment.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlyRefreshTokenRequestDto {

    @NotBlank(message = "refresh token 을 입력해 주세요.")
    private String refreshToken;

}
