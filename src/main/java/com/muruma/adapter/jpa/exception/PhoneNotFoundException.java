package com.muruma.adapter.jpa.exception;

import com.muruma.config.ErrorCode;
import com.muruma.config.exception.GenericException;

public class PhoneNotFoundException extends GenericException {
    public PhoneNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
