package com.lanzhu.testwork.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizRuntimeException extends RuntimeException {

    private ErrorCode errorCode;
    private Object extra;

    public BizRuntimeException() {
        super();
    }

    public BizRuntimeException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public BizRuntimeException(ErrorCode errorCode, Object extra) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        this.extra = extra;
    }

}
