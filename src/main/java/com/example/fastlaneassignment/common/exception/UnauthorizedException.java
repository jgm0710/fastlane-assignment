package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;

public class UnauthorizedException extends StatusException{

    public UnauthorizedException(ResponseCode responseCode) {
        super(responseCode);
    }

    public UnauthorizedException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
