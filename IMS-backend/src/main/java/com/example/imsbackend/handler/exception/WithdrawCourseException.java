package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.WITHDRAW_COURSE_ERROR;

public class WithdrawCourseException extends GlobalException {
    public WithdrawCourseException() {
        super(WITHDRAW_COURSE_ERROR.getCode(), WITHDRAW_COURSE_ERROR.getMessage());
    }
}
