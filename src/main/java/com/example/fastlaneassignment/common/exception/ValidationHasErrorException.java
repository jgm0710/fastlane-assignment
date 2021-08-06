package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import org.springframework.validation.Errors;

public class ValidationHasErrorException extends BadRequestException{

    private Errors errors;

    public ValidationHasErrorException(Errors errors) {
        super(ResponseCode.NOT_VALID);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

}
