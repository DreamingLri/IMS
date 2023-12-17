package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.DELETE_COURSE_ERROR;

public class DeleteCourseException extends GlobalException {
    public DeleteCourseException(String message) {
        super(DELETE_COURSE_ERROR.getCode(), message);
    }
}
