package com.github.liuzhuoming23.jwtback.common.exception;

/**
 * jwtback系统异常
 *
 * @author liuzhuoming
 */
public class JwtbackException extends RuntimeException {

    private static final long serialVersionUID = -635494975146845009L;

    public JwtbackException(String message) {
        super(message);
    }
}
