package com.example.fastlaneassignment.common;

public class FailResponse <T> {

    private String code;

    private T data;

    private String message;

    public FailResponse(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.data = data;
        this.message = responseCode.getMessage();
    }
}
