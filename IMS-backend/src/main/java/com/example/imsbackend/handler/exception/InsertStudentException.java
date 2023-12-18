package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.INSERT_STUDENT_ERROR;

public class InsertStudentException extends GlobalException {
    public InsertStudentException(String message) {
            super(INSERT_STUDENT_ERROR.getCode(), message);
    }
}
