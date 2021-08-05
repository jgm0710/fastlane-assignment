package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;

public class ConflictException extends StatusException{
    public ConflictException(ResponseCode responseCode) {
        super(responseCode);
    }

    public ConflictException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
