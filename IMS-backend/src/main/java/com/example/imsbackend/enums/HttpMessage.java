package com.example.imsbackend.enums;

import lombok.Getter;

import static com.example.imsbackend.constants.HttpStatus.*;

@Getter
public enum HttpMessage {
    SUCCESS(HTTP_STATUS_200, "success"),
    SYSTEM_ERROR(HTTP_STATUS_500, "system error"),
    USERNAME_PASSWORD_EXCEPTION(HTTP_STATUS_401,"username or password error"),
    INSERT_STUDENT_ERROR(HTTP_STATUS_500, "insert student error"),
    DELETE_STUDENT_ERROR(HTTP_STATUS_500, "delete student error");

    final Integer code;
    final String message;

    HttpMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
