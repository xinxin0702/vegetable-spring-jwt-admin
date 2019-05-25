package com.github.liuzhuoming23.jwtback.util;

import static com.github.liuzhuoming23.jwtback.common.cons.EncryptSalt.ACCOUNT_PASSWORD_SALT;

import org.apache.commons.lang3.StringUtils;

/**
 * 密码工具类
 *
 * @author liuzhuoming
 */
public class PswUtil {

    /**
     * 密码加密
     *
     * @param username 用户名
     * @param password 明文密码
     * @return 密文密码
     */
    public static String cipher(String username, String password) {
        return EncryptUtil.encode(username + ACCOUNT_PASSWORD_SALT + password, EncryptType.MD5);
    }

    /**
     * 检验密码是否正确
     *
     * @param username 用户名
     * @param password 明文密码
     * @param ciphertext 密文密码
     * @return true正确 false错误
     * @see PswUtil#cipher(String, String)
     */
    public static boolean isEquals(String username, String password, String ciphertext) {
        return StringUtils.isNotEmpty(ciphertext) && ciphertext.equals(EncryptUtil
            .encode(username + ACCOUNT_PASSWORD_SALT + password, EncryptType.MD5));
    }

}
