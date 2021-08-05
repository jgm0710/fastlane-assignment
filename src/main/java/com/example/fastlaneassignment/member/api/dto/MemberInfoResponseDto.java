package com.example.fastlaneassignment.member.api.dto;

import com.example.fastlaneassignment.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponseDto {

    private Long id;

    private String loginId;

    private LocalDateTime joinDate;

    public static MemberInfoResponseDto of(Member member) {
        return null;
    }
}
