package com.example.fastlaneassignment.auth.api.dto;

import com.example.fastlaneassignment.auth.service.dto.SignInDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    @NotBlank(message = "로그인 ID 를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    public SignInDto toServiceDto() {
        return new SignInDto(loginId, password);
    }
}
