package com.spamallday.payhere.exception;

public class CustomException extends RuntimeException {

    private CustomErrorCode customErrorCode;
    private String  detailMessage;

    public CustomException(CustomErrorCode customErrorCode){
        super(customErrorCode.getErrorMsg());
        this.customErrorCode = customErrorCode;
        this.detailMessage = customErrorCode.getErrorMsg();
    }

}
