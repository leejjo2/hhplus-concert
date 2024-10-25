package com.hhplusconcert.shared.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final IErrorType errorType;
    private final Object data;

    public ApplicationException(IErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

    public ApplicationException(IErrorType errorType) {
        this(errorType, null);
    }
}
