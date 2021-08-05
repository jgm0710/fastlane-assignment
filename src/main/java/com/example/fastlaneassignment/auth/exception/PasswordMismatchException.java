package com.example.fastlaneassignment.auth.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import com.example.fastlaneassignment.common.exception.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException() {
        super(ResponseCode.PASSWORD_MISMATCH);
    }
}
