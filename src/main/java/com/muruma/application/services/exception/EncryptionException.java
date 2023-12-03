package com.muruma.application.services.exception;

import com.muruma.config.ErrorCode;
import com.muruma.config.exception.GenericException;

public class EncryptionException extends GenericException {

    public EncryptionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EncryptionException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
