package com.muruma.config.exception;


import com.muruma.config.ErrorCode;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

public abstract class GenericException extends RuntimeException {
    private static final String SPACE = StringUtils.SPACE;
    private static final String COMMA = ",";
    private final ErrorCode errorCode;

    protected GenericException(ErrorCode errorCode) {
        super(errorCode.getReason());
        this.errorCode = errorCode;
    }

    protected GenericException(ErrorCode errorCode, String message) {
        super(buildMessage(message, errorCode));
        this.errorCode = errorCode;
    }

    protected GenericException(ErrorCode errorCode, String message, Throwable cause) {
        super(buildMessage(message, errorCode), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getCode() {
        return this.errorCode;
    }

    private static String buildMessage(String message, ErrorCode errorCode) {
        return String.format("%s%s%s%s", errorCode.getReason(), COMMA, SPACE, message);
    }
}
