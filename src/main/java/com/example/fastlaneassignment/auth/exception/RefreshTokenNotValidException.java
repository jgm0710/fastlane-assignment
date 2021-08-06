package com.example.fastlaneassignment.auth.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import com.example.fastlaneassignment.common.exception.BadRequestException;
import com.example.fastlaneassignment.common.exception.UnauthorizedException;

public class RefreshTokenNotValidException extends UnauthorizedException {
    public RefreshTokenNotValidException() {
        super(ResponseCode.REFRESH_TOKEN_NOT_VALID);
    }
}
