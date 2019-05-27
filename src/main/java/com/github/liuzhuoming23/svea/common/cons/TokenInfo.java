package com.github.liuzhuoming23.svea.common.cons;

import com.github.liuzhuoming23.svea.util.EncryptType;
import com.github.liuzhuoming23.svea.util.EncryptUtil;

/**
 * token信息
 *
 * @author liuzhuoming
 */
public class TokenInfo {

    public static final long EXPIRATION = 24 * 60 * 60 * 1000L;
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String JWT_SECRET = EncryptUtil.encode("1111/11/11", EncryptType.MD5);
}
