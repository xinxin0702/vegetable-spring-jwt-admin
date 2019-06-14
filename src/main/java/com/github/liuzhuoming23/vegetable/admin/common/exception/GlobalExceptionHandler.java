package com.github.liuzhuoming23.vegetable.admin.common.exception;

import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 系统异常统一处理
 *
 * @author liuzhuoming
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = VsjaException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handle(VsjaException e) {
        log.error(e.getMessage());
        return new Result().fail(e.getMessage());
    }

    @ExceptionHandler(value = TokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handle(TokenException e) {
        log.error(e.getMessage());
        return new Result().fail(HttpStatus.UNAUTHORIZED, e.getMessage(), "");
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handle(RuntimeException e) {
        log.error(e.getMessage());
        return new Result().fail(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handle(Exception e) {
        log.error(e.getMessage());
        return new Result().fail(e.getMessage());
    }
}
