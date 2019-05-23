package com.github.liuzhuoming23.jwtback.util;

import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 编码解码工具类
 *
 * @author liuzhuoming
 * @datetime 2019/5/6 16:25
 */
public class EncryptUtil {

    /**
     * 编码
     *
     * @param text 待编码文本
     * @param encryptType 编码类型
     */
    public static String encode(String text, EncryptType encryptType) {
        try {
            switch (encryptType) {
                case MD5:
                    return md5Encode(text);
                case BASE64:
                    return base64Encode(text);
                case URL:
                    return urlEncode(text);
                default:
                    throw new JwtbackException("encode type does not exist");
            }
        } catch (Exception e) {
            throw new JwtbackException("encode failed");
        }
    }

    /**
     * 解码
     *
     * @param text 待解码文本
     * @param encryptType 解码类型
     */
    public static String decode(String text, EncryptType encryptType) {
        try {
            switch (encryptType) {
                case BASE64:
                    return base64Decode(text);
                case URL:
                    return urlDecode(text);
                case MD5:
                default:
                    throw new JwtbackException("decode failed");
            }
        } catch (Exception e) {
            throw new JwtbackException("decode failed");
        }
    }


    /**
     * MD5摘要
     *
     * @param text 待编码文本
     * @return 编码后文本
     */
    private static String md5Encode(String text) {
        return DigestUtils.md5Hex(text);
    }

    /**
     * BASE64编码
     *
     * @param text 待编码文本
     * @return 编码后文本
     */
    private static String base64Encode(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * BASE64解码
     *
     * @param text 待解码文本
     * @return 解码后文本
     */
    private static String base64Decode(String text) {
        return new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8);
    }

    /**
     * URL编码
     *
     * @param text 待编码文本
     * @return 编码后文本
     */
    private static String urlEncode(String text) throws UnsupportedEncodingException {
        return URLEncoder.encode(text, "utf-8");
    }

    /**
     * URL解码
     *
     * @param text 待解码文本
     * @return 解码后文本
     */
    private static String urlDecode(String text) throws UnsupportedEncodingException {
        return URLDecoder.decode(text, "utf-8");
    }
}
