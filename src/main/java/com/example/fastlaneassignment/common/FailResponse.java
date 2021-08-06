package com.example.fastlaneassignment.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FailResponse<T> {

    private String code;

    private T data;

    private String message;

    public FailResponse(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.data = data;
        this.message = responseCode.getMessage();
    }

    public FailResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
