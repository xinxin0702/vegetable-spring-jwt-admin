package com.github.liuzhuoming23.vegetable.admin.common.cons;

import com.github.liuzhuoming23.vegetable.admin.util.EncryptType;
import com.github.liuzhuoming23.vegetable.admin.util.EncryptUtil;

/**
 * 加密盐
 *
 * @author liuzhuoming
 */
public class EncryptSalt {

    /**
     * 账户密码加盐
     */
    public static final String ACCOUNT_PASSWORD_SALT = EncryptUtil
        .encode("2222/22/22", EncryptType.MD5);
}
