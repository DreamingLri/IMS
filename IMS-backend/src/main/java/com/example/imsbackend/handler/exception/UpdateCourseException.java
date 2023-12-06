package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.UPDATE_COURSE_ERROR;

public class UpdateCourseException extends GlobalException {
    public UpdateCourseException() {
        super(UPDATE_COURSE_ERROR.getCode(), UPDATE_COURSE_ERROR.getMessage());
    }
}
