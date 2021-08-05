package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;

public class ForbiddenException extends StatusException{
    public ForbiddenException(ResponseCode responseCode) {
        super(responseCode);
    }

    public ForbiddenException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
