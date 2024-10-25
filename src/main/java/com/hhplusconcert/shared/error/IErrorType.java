package com.hhplusconcert.shared.error;

import org.springframework.boot.logging.LogLevel;

public interface IErrorType {

    ErrorCode getCode();

    String getMessage();

    LogLevel getLogLevel();
}
