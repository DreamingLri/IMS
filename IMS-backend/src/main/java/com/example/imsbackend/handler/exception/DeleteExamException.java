package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.DELETE_EXAM_ERROR;

public class DeleteExamException extends GlobalException {
    public DeleteExamException() {
        super(DELETE_EXAM_ERROR.getCode(), DELETE_EXAM_ERROR.getMessage());
    }
}
