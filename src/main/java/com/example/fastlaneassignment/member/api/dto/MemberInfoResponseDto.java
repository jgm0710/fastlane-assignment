package com.example.fastlaneassignment.member.api.dto;

import com.example.fastlaneassignment.common.DateFormatUtils;
import com.example.fastlaneassignment.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponseDto {

    private Long id;

    private String loginId;

    private String joinDate;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .joinDate(DateFormatUtils.format(member.getCreateDate()))
                .build();
    }
}
