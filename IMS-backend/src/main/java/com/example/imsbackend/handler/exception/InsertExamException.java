package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.INSERT_EXAM_ERROR;

public class InsertExamException extends GlobalException {
    public InsertExamException() {
        super(INSERT_EXAM_ERROR.getCode(), INSERT_EXAM_ERROR.getMessage());
    }
}
