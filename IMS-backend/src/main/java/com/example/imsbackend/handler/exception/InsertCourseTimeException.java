package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;
import com.google.protobuf.GeneratedMessage;

public class InsertCourseTimeException extends GlobalException {
    public InsertCourseTimeException(String message) {
        super(422, message);
    }
}
