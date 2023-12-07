package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.*;

public class UpdateExamException extends GlobalException {
    public UpdateExamException() {
        super(UPDATE_EXAM_ERROR.getCode(), UPDATE_EXAM_ERROR.getMessage());
    }
}
