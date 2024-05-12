package com.example.shopping.global.exception;

import com.example.shopping.global.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private ErrorCode errorCode;


    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
