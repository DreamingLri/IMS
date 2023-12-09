package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.SELECT_COURSE_ERROR;

public class SelectCourseException extends GlobalException {
    public SelectCourseException() {
        super(SELECT_COURSE_ERROR.getCode(), SELECT_COURSE_ERROR.getMessage());
    }
}
