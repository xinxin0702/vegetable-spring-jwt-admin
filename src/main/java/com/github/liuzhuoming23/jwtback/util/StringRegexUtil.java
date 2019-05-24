package com.github.liuzhuoming23.jwtback.util;

/**
 * 字符串正则验证
 *
 * @author liuzhuoming
 */
public class StringRegexUtil {

    /**
     * 字符串是否只包含大小写字符和数字
     *
     * @param str 需验证字符串
     * @return true是，false否
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }
}
