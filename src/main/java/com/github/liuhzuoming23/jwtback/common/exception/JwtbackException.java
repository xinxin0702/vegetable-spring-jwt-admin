package com.github.liuhzuoming23.jwtback.common.exception;

/**
 * jwtback系统异常
 *
 * @author gnimouhzuil
 * @date 2019/5/23 22:15
 */
public class JwtbackException extends RuntimeException {

    private static final long serialVersionUID = -635494975146845009L;

    public JwtbackException(String message) {
        super(message);
    }
}
