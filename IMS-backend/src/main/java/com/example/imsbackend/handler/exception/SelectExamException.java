package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.SELECT_EXAM_ERROR;

public class SelectExamException extends GlobalException {
    public SelectExamException() {
        super(SELECT_EXAM_ERROR.getCode(), SELECT_EXAM_ERROR.getMessage());
    }
}
