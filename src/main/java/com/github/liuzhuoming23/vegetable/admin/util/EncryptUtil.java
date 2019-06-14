package com.github.liuzhuoming23.vegetable.admin.util;

import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 编码解码工具类
 *
 * @author liuzhuoming
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
                case AES:
                    return aesEncode(text);
                default:
                    throw new VsjaException("encode type does not exist");
            }
        } catch (Exception e) {
            throw new VsjaException("encode failed");
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
                case AES:
                    return aesDecode(text);
                case MD5:
                default:
                    throw new VsjaException("decode failed");
            }
        } catch (Exception e) {
            throw new VsjaException("decode failed");
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

    /**
     * AES加密
     *
     * @param text 待加密文本
     * @param key 加密密钥
     */
    private static String aesEncode(String text, String key)
        throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        //构造密钥生成器，指定为AES算法，不区分大小写
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = text.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] bytes = cipher.doFinal(byteContent);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * AES解密
     *
     * @param text 待解密文本
     * @param key 解密密钥
     */
    private static String aesDecode(String text, String key)
        throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        if (text.length() < 1) {
            return null;
        }
        byte[] bytes = new byte[text.length() / 2];
        for (int i = 0; i < text.length() / 2; i++) {
            int high = Integer.parseInt(text.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(text.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high * 16 + low);
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] result = cipher.doFinal(bytes);
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * AES默认密钥
     */
    private final static String AES_KEY = EncryptUtil.encode("1990-12-23", EncryptType.MD5);

    /**
     * AES默认密钥加密
     *
     * @param text 待加密文本
     */
    private static String aesEncode(String text)
        throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return aesEncode(text, AES_KEY);
    }

    /**
     * AES默认密钥解密
     *
     * @param text 待解密文本
     */
    private static String aesDecode(String text)
        throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return aesDecode(text, AES_KEY);
    }
}
