package com.muruma.adapter.jpa.exception;

import com.muruma.config.ErrorCode;
import com.muruma.config.exception.GenericException;

public class PhoneNumberException extends GenericException {
    public PhoneNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
