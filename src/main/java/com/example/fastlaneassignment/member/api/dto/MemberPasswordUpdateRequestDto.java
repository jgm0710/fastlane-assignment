package com.example.fastlaneassignment.member.api.dto;

import com.example.fastlaneassignment.member.service.dto.PasswordUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPasswordUpdateRequestDto {
    private String oldPassword;
    private String newPassword;

    public PasswordUpdateDto toServiceDto() {
        return new PasswordUpdateDto(oldPassword, newPassword);
    }
}
