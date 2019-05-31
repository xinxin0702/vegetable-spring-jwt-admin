package com.github.liuzhuoming23.vegetable.admin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * query参数构建辅助类
 *
 * @author liuzhuoming
 */
public class QueryParamsUtil {

    /**
     * 把请求元素按照“参数=参数值”的模式用“&”字符拼接成字符串（排序，进行urlEncode，不可以为null）
     *
     * @param params 请求参数
     * @param excludeKeys 不参与签名的参数集合
     * @return 拼接成的字符串
     */
    public static String generateParamsStringWithSortAndUrlEncodeAndNotNullable(
        Map<String, String> params, Set<String> excludeKeys) {
        return generateParamsString(params, true, true, false, false, excludeKeys);
    }

    /**
     * 把请求元素按照“参数=参数值”的模式用“&”字符拼接成字符串（排序，不进行urlEncode，不可以为null）
     *
     * @param params 请求参数
     * @param excludeKeys 不参与签名的参数集合
     * @return 拼接成的字符串
     */
    public static String generateParamsStringWithSortAndNotUrlEncodeAndNotNullable(
        Map<String, String> params, Set<String> excludeKeys) {
        return generateParamsString(params, true, false, false, false, excludeKeys);
    }

    /**
     * 把请求元素按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 请求参数
     * @param sort 是否需要根据key值作升序排列
     * @param encode 是否需要URL编码
     * @param nullable 是否允许null值参数参与签名
     * @param retainEqualSign 在{nullable}为true的情况下，是否保留参数拼接的等于号。{nullable}为false则不生效（假设b=null，如果{retainEqualSign}为true，则拼接字符串格式为?a=1&b=&c=3；如果{retainEqualSign}为false，则拼接字符串格式为?a=1&b&c=3）
     * @param excludeKeys 不参与签名的参数集合
     * @return 拼接成的字符串
     * @see QueryParamsUtil#generateParamsString(Map, boolean, boolean, boolean, boolean, String...)
     */
    public static String generateParamsString(Map<String, String> params, boolean sort,
        boolean encode, boolean nullable, boolean retainEqualSign, Set<String> excludeKeys) {
        return generateParamsString(params, sort, encode, nullable, retainEqualSign,
            excludeKeys.toArray(new String[0]));
    }

    /**
     * 把请求元素按照“参数=参数值”的模式用“&”字符拼接成字符串（排序，进行urlEncode，不可以为null）
     *
     * @param params 请求参数
     * @param excludeKeys 不参与签名的参数集合
     * @return 拼接成的字符串
     */
    public static String generateParamsStringWithSortAndUrlEncodeAndNotNullable(
        Map<String, String> params, String... excludeKeys) {
        return generateParamsString(params, true, true, false, false, excludeKeys);
    }

    /**
     * 把请求元素按照“参数=参数值”的模式用“&”字符拼接成字符串（排序，不进行urlEncode，不可以为null）
     *
     * @param params 请求参数
     * @param excludeKeys 不参与签名的参数集合
     * @return 拼接成的字符串
     */
    public static String generateParamsStringWithSortAndNotUrlEncodeAndNotNullable(
        Map<String, String> params, String... excludeKeys) {
        return generateParamsString(params, true, false, false, false, excludeKeys);
    }

    /**
     * 把请求元素按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 请求参数
     * @param sort 是否需要根据key值作升序排列
     * @param encode 是否需要URL编码
     * @param nullable 是否允许null值参数参与签名
     * @param retainEqualSign 在{nullable}为true的情况下，是否保留参数拼接的等于号。{nullable}为false则不生效（假设b=null，如果{retainEqualSign}为true，则拼接字符串格式为?a=1&b=&c=3；如果{retainEqualSign}为false，则拼接字符串格式为?a=1&b&c=3）
     * @param excludeKeys 不参与签名的参数数组
     * @return 拼接成的字符串
     */
    public static String generateParamsString(Map<String, String> params, boolean sort,
        boolean encode, boolean nullable, boolean retainEqualSign, String... excludeKeys) {
        //拷贝原参数map，防止因为删除元素导致原map元素发生改变
        Map<String, String> clone = new HashMap<>(params);
        if (!nullable) {
            removeNullValueElementInMap(clone);
        }
        if (excludeKeys != null && excludeKeys.length > 0) {
            removeExcludeKeysElementsInMap(clone, excludeKeys);
        }
        List<String> keys = new ArrayList<>(clone.keySet());
        if (sort) {
            Collections.sort(keys);
        }
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = clone.get(key);
            if (value != null) {
                if (encode) {
                    value = EncryptUtil.encode(value, EncryptType.URL);
                }
                sb.append(key).append("=").append(value).append("&");
            } else {
                if (retainEqualSign) {
                    sb.append(key).append("=").append("&");
                } else {
                    sb.append(key).append("&");
                }
            }
        }
        //删除最后多余的&
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 移除map中的空值元素（会影响传入的map元素）
     *
     * @param params 参数map
     */
    private static void removeNullValueElementInMap(Map<String, String> params) {
        Set<String> removeKeys = new HashSet<>();
        //获得值为null的元素集合
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) {
                removeKeys.add(key);
            }
        }
        for (String key : removeKeys) {
            params.remove(key);
        }
    }

    /**
     * 移除需排除的key元素（会影响传入的map元素）
     *
     * @param params 参数map
     * @param excludeKeys 需移除元素key数组
     */
    private static void removeExcludeKeysElementsInMap(Map<String, String> params,
        String... excludeKeys) {
        for (String key : excludeKeys) {
            params.remove(key);
        }
    }
}
