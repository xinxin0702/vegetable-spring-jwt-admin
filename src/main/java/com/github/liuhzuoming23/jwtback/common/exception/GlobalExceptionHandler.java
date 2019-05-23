package com.github.liuhzuoming23.jwtback.common.exception;

import com.github.liuhzuoming23.jwtback.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * jwtback系统异常统一处理
 *
 * @author gnimouhzuil
 * @date 2019/5/23 23:15
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
