package com.github.liuzhuoming23.jwtback.common.cons;

import com.github.liuzhuoming23.jwtback.util.EncryptType;
import com.github.liuzhuoming23.jwtback.util.EncryptUtil;

/**
 * token cons
 *
 * @author liuzhuoming
 */
public class TokenCons {

    public static final long EXPIRATION = 24 * 60 * 60 * 1000L;
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String JWT_SECRET = EncryptUtil.encode("1111/11/11", EncryptType.MD5);
}
