package com.muruma.adapter.jpa.exception;

import com.muruma.config.ErrorCode;
import com.muruma.config.exception.GenericException;

public class UserEmailException extends GenericException {
    public UserEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
