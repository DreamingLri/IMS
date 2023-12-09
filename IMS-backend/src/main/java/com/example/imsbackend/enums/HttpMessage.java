package com.example.imsbackend.enums;

import lombok.Getter;

import static com.example.imsbackend.constants.HttpStatus.*;

@Getter
public enum HttpMessage {
    SUCCESS(HTTP_STATUS_200, "success"),
    SYSTEM_ERROR(HTTP_STATUS_500, "system error"),
    USERNAME_PASSWORD_EXCEPTION(HTTP_STATUS_401,"username or password error"),
    INSERT_STUDENT_ERROR(HTTP_STATUS_500, "insert student error"),
    DELETE_STUDENT_ERROR(HTTP_STATUS_500, "delete student error"),
    UPDATE_STUDENT_ERROR(HTTP_STATUS_500, "update student error"),
    INSERT_COURSE_ERROR(HTTP_STATUS_500, "insert course error"),
    DELETE_COURSE_ERROR(HTTP_STATUS_500, "delete course error"),
    UPDATE_COURSE_ERROR(HTTP_STATUS_500, "update course error"),
    INSERT_EXAM_ERROR(HTTP_STATUS_500, "insert exam error"),
    DELETE_EXAM_ERROR(HTTP_STATUS_500, "delete exam error"),
    UPDATE_EXAM_ERROR(HTTP_STATUS_500, "update exam error"),

    SELECT_COURSE_ERROR(HTTP_STATUS_500, "select course error"),
    WITHDRAW_COURSE_ERROR(HTTP_STATUS_500, "withdraw course error");

    final Integer code;
    final String message;

    HttpMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
