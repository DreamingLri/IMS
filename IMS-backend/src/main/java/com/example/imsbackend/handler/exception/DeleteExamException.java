package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.DELETE_EXAM_ERROR;

public class DeleteExamException extends GlobalException {
    public DeleteExamException(String message) {
        super(DELETE_EXAM_ERROR.getCode(), message);
    }
}
