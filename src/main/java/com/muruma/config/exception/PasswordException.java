package com.muruma.config.exception;

import com.muruma.config.ErrorCode;

public class PasswordException extends GenericException {
    public PasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
