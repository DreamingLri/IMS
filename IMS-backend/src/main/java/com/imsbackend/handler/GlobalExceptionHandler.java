package com.imsbackend.handler;

import com.imsbackend.domain.ResultBean;
import com.imsbackend.enums.HttpMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResultBean<Void> exceptionHandler(Exception e){
        return ResultBean.error(HttpMessage.SYSTEM_ERROR);
    }
}
