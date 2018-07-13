package com.lanzhu.testwork.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends Exception {

    private ErrorCode errorCode;
    private Object extra;

    public BizException() {
        super();
    }

    public BizException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, Object extra) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        this.extra = extra;
    }
}
