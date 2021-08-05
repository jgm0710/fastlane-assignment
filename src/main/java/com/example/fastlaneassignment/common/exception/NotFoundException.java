package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;

public class NotFoundException extends StatusException{

    public NotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }

    public NotFoundException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }

}
