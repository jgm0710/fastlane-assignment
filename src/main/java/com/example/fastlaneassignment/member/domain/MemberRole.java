package com.example.fastlaneassignment.member.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberRole {
    USER("일반 회원 권한","ROLE_USER"),
    ADMIN("관리자 권한","ROLE_USER")
    ;

    private final String description;
    private final String roleName;

    public String getRoleName() {
        return roleName;
    }
}
