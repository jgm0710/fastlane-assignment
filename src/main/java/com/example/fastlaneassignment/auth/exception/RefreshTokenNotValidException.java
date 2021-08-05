package com.example.fastlaneassignment.auth.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import com.example.fastlaneassignment.common.exception.BadRequestException;

public class RefreshTokenNotValidException extends BadRequestException {
    public RefreshTokenNotValidException() {
        super(ResponseCode.REFRESH_TOKEN_NOT_VALID);
    }
}
