package com.example.fastlaneassignment.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlyRefreshTokenRequestDto {

    private String refreshToken;

}
