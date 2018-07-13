package com.lanzhu.testwork.exception;

import com.alibaba.fastjson.JSONObject;

public enum ErrorCode {

    USER_PHONE_ERROR(1001, "用户手机号错误", ErrorDisplay.MODAL),
    USER_PHONE_EMPTY(1002, "用户手机号为空", ErrorDisplay.MODAL),

    PARAM_ERROR(1000, "参数错误"),
    SYS_ERROR(9999, "系统异常");

    private int errorCode;
    private String errorMessage;
    private ErrorDisplay errorDisplay;

    private ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDisplay = ErrorDisplay.NOTICE; //默认
    }

    private ErrorCode(int errorCode, String errorMessage, ErrorDisplay errorDisplay) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDisplay = errorDisplay; //默认
    }

    public String toString() {
        JSONObject json = new JSONObject();
        json.put("errorCode", this.errorCode);
        json.put("errorMessage", this.errorMessage);
        json.put("errorDisplay", this.errorDisplay);
        return json.toString();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorDisplay getErrorDisplay() {
        return errorDisplay;
    }

    public void setErrorDisplay(ErrorDisplay errorDisplay) {
        this.errorDisplay = errorDisplay;
    }
}
