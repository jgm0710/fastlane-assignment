package com.example.fastlaneassignment.member.api.dto;

import com.example.fastlaneassignment.member.service.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequestDto {

    private String loginId;

    private String password;

    public SignUpDto toServiceDto() {
        return new SignUpDto(loginId, password);
    }
}
