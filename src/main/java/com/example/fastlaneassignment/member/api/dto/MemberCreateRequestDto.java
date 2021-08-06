package com.example.fastlaneassignment.member.api.dto;

import com.example.fastlaneassignment.member.service.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequestDto {

    @NotBlank(message = "로그인 ID 를 기입해 주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 기입해 주세요.")
    private String password;

    public SignUpDto toServiceDto() {
        return new SignUpDto(loginId, password);
    }
}
