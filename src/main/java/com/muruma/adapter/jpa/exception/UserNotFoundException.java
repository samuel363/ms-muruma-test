package com.muruma.adapter.jpa.exception;

import com.muruma.config.ErrorCode;
import com.muruma.config.exception.GenericException;

public class UserNotFoundException extends GenericException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
