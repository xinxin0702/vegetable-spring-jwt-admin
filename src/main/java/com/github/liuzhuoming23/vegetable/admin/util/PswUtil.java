package com.github.liuzhuoming23.vegetable.admin.util;

import com.github.liuzhuoming23.vegetable.admin.common.cons.EncryptSalt;
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
    public static String encrypt(String username, String password) {
        return EncryptUtil.encode(username + EncryptSalt.ACCOUNT_PASSWORD_SALT + password, EncryptType.MD5);
    }

    /**
     * 检验密码是否正确
     *
     * @param username 用户名
     * @param password 明文密码
     * @param encryptedText 密文密码
     * @return true正确 false错误
     * @see PswUtil#encrypt(String, String)
     */
    public static boolean isEquals(String username, String password, String encryptedText) {
        return StringUtils.isNotEmpty(encryptedText) && encryptedText
            .equals(encrypt(username, password));
    }

}
