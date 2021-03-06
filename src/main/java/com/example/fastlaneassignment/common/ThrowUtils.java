package com.example.fastlaneassignment.common;

import com.example.fastlaneassignment.common.exception.ValidationHasErrorException;
import org.springframework.validation.Errors;

public class ThrowUtils {
    public static void hasErrorsThrow(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationHasErrorException(errors);
        }
    }
}
