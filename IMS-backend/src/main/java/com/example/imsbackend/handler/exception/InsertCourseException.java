package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.INSERT_COURSE_ERROR;

public class InsertCourseException extends GlobalException {
    public InsertCourseException() {
        super(INSERT_COURSE_ERROR.getCode(), INSERT_COURSE_ERROR.getMessage());
    }
}
