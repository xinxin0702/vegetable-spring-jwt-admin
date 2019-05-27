package com.github.liuzhuoming23.svea.common.cons;

/**
 * redis key
 *
 * @author liuzhuoming
 */
public class RedisKey {

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
    /**
     * limit hash key
     */
    public static final String LIMIT_HASH_KEY = "limits";
}
