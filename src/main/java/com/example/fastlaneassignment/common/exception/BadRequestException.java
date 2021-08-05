package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import org.apache.tomcat.jni.Status;

public class BadRequestException extends StatusException {
    public BadRequestException(ResponseCode responseCode) {
        super(responseCode);
    }

    public BadRequestException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
