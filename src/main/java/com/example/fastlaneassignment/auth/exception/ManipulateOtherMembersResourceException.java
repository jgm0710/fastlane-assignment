package com.example.fastlaneassignment.auth.exception;

import com.example.fastlaneassignment.common.ResponseCode;
import com.example.fastlaneassignment.common.exception.ForbiddenException;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;

public class ManipulateOtherMembersResourceException extends ForbiddenException {
    public ManipulateOtherMembersResourceException() {
        super(ResponseCode.MANIPULATE_OTHER_MEMBERS_RESOURCE);
    }
}
