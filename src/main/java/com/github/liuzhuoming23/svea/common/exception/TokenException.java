package com.github.liuzhuoming23.svea.common.exception;

/**
 * token验证异常
 *
 * @author liuzhuoming
 */
public class TokenException extends RuntimeException {

    private static final long serialVersionUID = 3884934558888680613L;

    public TokenException(String message) {
        super(message);
    }
}
