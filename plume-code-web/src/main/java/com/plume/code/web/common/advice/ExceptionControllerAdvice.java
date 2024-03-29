package com.plume.code.web.common.advice;

import com.plume.code.web.controller.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public R<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return R.fail(ex.getMessage());
    }

}
