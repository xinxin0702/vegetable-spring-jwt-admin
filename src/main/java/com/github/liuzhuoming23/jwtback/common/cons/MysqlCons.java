package com.github.liuzhuoming23.jwtback.common.cons;

import com.github.liuzhuoming23.jwtback.util.EncryptType;
import com.github.liuzhuoming23.jwtback.util.EncryptUtil;

/**
 * mysql cons
 *
 * @author liuzhuoming
 */
public class MysqlCons {

    /**
     * 账户密码加盐
     */
    public static final String ACCOUNT_PASSWORD_SALT = EncryptUtil
        .encode("2222/22/22", EncryptType.MD5);
}
