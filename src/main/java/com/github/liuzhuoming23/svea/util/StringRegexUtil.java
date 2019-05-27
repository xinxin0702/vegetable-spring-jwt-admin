package com.github.liuzhuoming23.svea.util;

/**
 * 字符串正则验证
 *
 * @author liuzhuoming
 */
public class StringRegexUtil {

    /**
     * 是否只包含大小写字符或数字
     *
     * @param str 需验证字符串
     * @param min 最小长度
     * @param max 最大长度
     * @return true是，false否
     */
    public static boolean isContainLetterOrDigit(String str, int min, int max) {
        String regex = String.format("^[a-z0-9A-Z]{%d,%d}$", min, max);
        return str.matches(regex);
    }

    /**
     * 是否同时包含大小写字母和数字
     *
     * @param str 需验证字符串
     * @param min 最小长度
     * @param max 最大长度
     * @return true是，false否
     */
    public static boolean isContainUppercaseAndLowercaseAndDigit(String str, int min, int max) {
        String regex = String
            .format("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{%d,%d}$", min, max);
        return str.matches(regex);
    }
}
