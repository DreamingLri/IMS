package com.example.imsbackend.handler;

import com.example.imsbackend.domain.ResultBean;
import com.example.imsbackend.enums.HttpMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultBean<Void> exceptionHandler(Exception e){
        if(e instanceof GlobalException exception){
            return ResultBean.error(exception.getCode(), exception.getMessage());
        }
        return ResultBean.error(HttpMessage.SYSTEM_ERROR);
    }
}
