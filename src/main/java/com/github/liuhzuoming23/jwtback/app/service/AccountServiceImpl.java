package com.github.liuhzuoming23.jwtback.app.service;

import com.github.liuhzuoming23.jwtback.app.domain.Account;
import com.github.liuhzuoming23.jwtback.app.mapper.AccountMapper;
import com.github.liuhzuoming23.jwtback.common.exception.JwtbackException;
import com.github.liuhzuoming23.jwtback.common.cons.RedisKeys;
import com.github.liuhzuoming23.jwtback.common.redis.RedisOperation;
import com.github.liuhzuoming23.jwtback.util.EncryptType;
import com.github.liuhzuoming23.jwtback.util.EncryptUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 账户service
 *
 * @author gnimouhzuil
 * @date 2019/5/23 21:43
 */
@Service
@CacheConfig(cacheNames = RedisKeys.CACHE_ACCOUNT_PREFIX)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RedisOperation redisOperation;

    @Override
    @CachePut(key = "#p0.username")
    public Account insert(Account account) {
        List<Account> list = accountMapper.select(account);
        if (list != null && list.size() > 0) {
            throw new JwtbackException("user already exists");
        }
        account.setPassword(EncryptUtil.encode(account.getUsername() + account.getPassword(),
            EncryptType.MD5));
        accountMapper.insert(account);
        return accountMapper.selectOneByName(account.getUsername());
    }

    @Override
    public List<Account> select(Account account) {
        return accountMapper.select(account);
    }

    @Override
    @Cacheable(key = "#p0")
    public Account selectOneByName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        Account account = accountMapper.selectOneByName(username);
        if (account == null) {
            redisOperation.value().delete(RedisKeys.CACHE_ACCOUNT_PREFIX + "::" + username);
        }
        return account;
    }
}
