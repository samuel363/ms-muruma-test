package com.muruma.config.exception;

import com.muruma.config.ErrorCode;

public class UserLoginException extends GenericException {
    public UserLoginException(ErrorCode errorCode) {
        super(errorCode);
    }
}
