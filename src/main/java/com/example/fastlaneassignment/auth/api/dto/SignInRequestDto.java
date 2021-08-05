package com.example.fastlaneassignment.auth.api.dto;

import com.example.fastlaneassignment.auth.service.dto.SignInDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    private String loginId;

    private String password;

    public SignInDto toServiceDto() {
        return new SignInDto(loginId, password);
    }
}
