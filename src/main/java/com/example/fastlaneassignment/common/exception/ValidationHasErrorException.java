package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import org.springframework.validation.Errors;

public class ValidationHasErrorException extends BadRequestException{

    private Errors errors;

    public ValidationHasErrorException(ResponseCode responseCode, Errors errors) {
        super(responseCode);
        this.errors = errors;
    }

    public ValidationHasErrorException(ResponseCode responseCode, String message, Errors errors) {
        super(responseCode, message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

}
