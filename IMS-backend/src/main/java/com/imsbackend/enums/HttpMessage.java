package com.imsbackend.enums;

import lombok.Getter;

import static com.imsbackend.constans.HttpStatus.*;

@Getter
public enum HttpMessage {
    SUCCESS(HTTP_STATUS_200, "success"),
    SYSTEM_ERROR(HTTP_STATUS_500, "unknown error");

    final Integer code;
    final String message;

    HttpMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
