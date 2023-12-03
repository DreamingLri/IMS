package com.example.imsbackend.handler.exception;

import com.example.imsbackend.handler.GlobalException;

import static com.example.imsbackend.enums.HttpMessage.USERNAME_PASSWORD_EXCEPTION;

public class UsernamePasswordException extends GlobalException {
    public UsernamePasswordException() {
        super(USERNAME_PASSWORD_EXCEPTION.getCode(), USERNAME_PASSWORD_EXCEPTION.getMessage());
    }
}
