package com.example.fastlaneassignment.member.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import com.example.fastlaneassignment.common.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super(ResponseCode.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException(String loginId) {
        super(ResponseCode.MEMBER_NOT_FOUND, ResponseCode.MEMBER_NOT_FOUND.getMessage() + " ID : " + loginId);
    }
}
