package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;

public class StatusException extends RuntimeException{

    private String code;

    public StatusException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public StatusException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }
}
