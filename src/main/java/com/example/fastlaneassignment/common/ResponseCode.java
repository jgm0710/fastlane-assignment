package com.example.fastlaneassignment.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseCode implements EnumType{

    /**
     * authentication
     */
    PASSWORD_MISMATCH("AT001", "비밀번호가 일치하지 않습니다.", "기존 회원의 비밀번호와 일치하는지 확인되는 비밀번호가 일치하지 않을 경우"),
    MANIPULATE_OTHER_MEMBERS_RESOURCE("AT002", "다른 회원의 리소스는 조작할 수 없습니다.", "다른 회원의 리소스를 조작하는 경우"),
    REFRESH_TOKEN_NOT_VALID("AT003", "Refresh Token 이 유효하지 않습니다.","인증 토큰 재발급에 필요한 Refresh Token 이 유효하지 않은 경우"),

    /**
     * member
     */
    MEMBER_NOT_FOUND("M001", "회원을 찾을 수 없습니다.", "회원 조회, 로그인 등에서 회원을 찾을 수 없는 경우"),
    LOGIN_ID_DUPLICATE("M002", "해당 로그인 ID 로 가입된 회원이 이미 존재합니다.", "로그인 ID 가 중복되는 경우"),


    /**
     * common
     */
    UNAUTHORIZED("CM001", "인증되지 않은 사용자 입니다.", "Access Token 을 통한 인증이 통과되지 않은 회원인 경우"),
    FORBIDDEN("CM002", "해당 리소스에 접근 권한이 없는 사용자 입니다.", "인증은 되었으나, 리소스에 접근할 권한이 없는 회원인 경우"),
    ;

    private final String code;
    private final String message;
    private final String description;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getDescription() {
        return description;
    }
}