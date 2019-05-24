package com.github.liuzhuoming23.jwtback.common.cons;

/**
 * redis cons
 *
 * @author liuzhuoming
 */
public class RedisCons {

    /**
     * 缓存连接符
     */
    public static final String CACHE_KEY_LINK_SYMBOL = "::";

    /**
     * 账户redis缓存前缀
     */
    public static final String CACHE_KEY_ACCOUNT_PREFIX = "account";
    /**
     * 字典redis缓存前缀
     */
    public static final String CACHE_KEY_DICT_PREFIX = "dict";

    /**
     * token hash key
     */
    public static final String TOKEN_HASH_KEY = "tokens";
}
