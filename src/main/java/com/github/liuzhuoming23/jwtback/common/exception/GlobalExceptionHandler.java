package com.github.liuzhuoming23.jwtback.common.exception;

import com.github.liuzhuoming23.jwtback.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * jwtback系统异常统一处理
 *
 * @author liuzhuoming
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = JwtbackException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(JwtbackException e) {
        log.error(e.getMessage());
        return new Result().fail(e.getMessage());
    }
}
