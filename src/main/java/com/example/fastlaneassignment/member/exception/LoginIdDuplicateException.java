package com.example.fastlaneassignment.member.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import com.example.fastlaneassignment.common.exception.ConflictException;

public class LoginIdDuplicateException extends ConflictException {
    public LoginIdDuplicateException() {
        super(ResponseCode.LOGIN_ID_DUPLICATE);
    }
}
