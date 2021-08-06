package com.example.fastlaneassignment.member.api.dto;

import com.example.fastlaneassignment.member.service.dto.PasswordUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPasswordUpdateRequestDto {

    @NotBlank(message = "본인 확인을 위한 기존 비밀번호를 입력해 주세요.")
    private String oldPassword;
    @NotBlank(message = "변경할 비밀번호를 입력해 주세요.")
    private String newPassword;

    public PasswordUpdateDto toServiceDto() {
        return new PasswordUpdateDto(oldPassword, newPassword);
    }
}
