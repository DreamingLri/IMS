package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.UPDATE_STUDENT_ERROR;

public class UpdateStudentException extends GlobalException {
    public UpdateStudentException() {
        super(UPDATE_STUDENT_ERROR.getCode(), UPDATE_STUDENT_ERROR.getMessage());
    }
}
