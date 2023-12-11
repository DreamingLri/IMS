package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.WITHDRAW_EXAM_ERROR;

public class WithdrawExamException extends GlobalException {
    public WithdrawExamException() {
        super(WITHDRAW_EXAM_ERROR.getCode(), WITHDRAW_EXAM_ERROR.getMessage());
    }
}
