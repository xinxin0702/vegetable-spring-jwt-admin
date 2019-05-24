package com.github.liuzhuoming23.jwtback.app.service.impl;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import com.github.liuzhuoming23.jwtback.app.mapper.AccountMapper;
import com.github.liuzhuoming23.jwtback.app.service.AccountService;
import com.github.liuzhuoming23.jwtback.common.cons.RedisCons;
import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import com.github.liuzhuoming23.jwtback.util.PswUtil;
import com.github.liuzhuoming23.jwtback.util.StringRegexUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 账户service
 *
 * @author liuzhuoming
 */
@Service
@CacheConfig(cacheNames = RedisCons.CACHE_KEY_ACCOUNT_PREFIX)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @CachePut(key = "#p0.username")
    public Account insert(Account account) {
        if (!StringRegexUtil.isContainLetterOrDigit(account.getUsername(), 6, 16)) {
            throw new JwtbackException(
                "username can only contain uppercase or lowercase letters or numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(account.getPassword(), 6, 16)) {
            throw new JwtbackException(
                "password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }
        Account list = accountMapper.selectOneByUsername(account.getUsername());
        if (list != null) {
            throw new JwtbackException("account already exists");
        }
        account.setPassword(PswUtil.cipher(account.getUsername(), account.getPassword()));
        accountMapper.insert(account);
        return accountMapper.selectOneByUsername(account.getUsername());
    }

    @Override
    public List<Account> select(Account account) {
        return accountMapper.select(account);
    }

    @Override
    @Cacheable(key = "#p0")
    public Account selectOneByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new JwtbackException("account not exist");
        }
        Account account = accountMapper.selectOneByUsername(username);
        if (account == null) {
            throw new JwtbackException("account not exist");
        }
        return account;
    }

    @Override
    @CachePut(key = "#p0.username")
    public Account updatePasswordByUsername(Account account) {
        account.setPassword(PswUtil.cipher(account.getUsername(), account.getPassword()));
        accountMapper.updatePasswordByUsername(account);
        return accountMapper.selectOneByUsername(account.getUsername());
    }

    @Override
    @CacheEvict(key = "#p0")
    public void deleteOneByUsername(String username) {
        accountMapper.deleteOneByUsername(username);
    }
}
