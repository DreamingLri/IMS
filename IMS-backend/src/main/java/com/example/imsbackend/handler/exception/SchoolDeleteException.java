package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.DELETE_SCHOOL_ERROR;

public class SchoolDeleteException extends GlobalException {
    public SchoolDeleteException() {
        super(DELETE_SCHOOL_ERROR.getCode(), DELETE_SCHOOL_ERROR.getMessage());
    }
}
