package com.github.liuzhuoming23.jwtback.app.service;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import com.github.liuzhuoming23.jwtback.app.mapper.AccountMapper;
import com.github.liuzhuoming23.jwtback.common.cons.RedisKeys;
import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import com.github.liuzhuoming23.jwtback.util.EncryptType;
import com.github.liuzhuoming23.jwtback.util.EncryptUtil;
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
@CacheConfig(cacheNames = RedisKeys.CACHE_ACCOUNT_PREFIX)
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
        List<Account> list = accountMapper.select(account);
        if (list != null && list.size() > 0) {
            throw new JwtbackException("account already exists");
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
        return accountMapper.selectOneByName(username);
    }

    @Override
    @CachePut(key = "#p0.username")
    public Account updatePasswordByUsername(Account account) {
        account.setPassword(EncryptUtil.encode(account.getUsername() + account.getPassword(),
            EncryptType.MD5));
        accountMapper.updatePasswordByUsername(account);
        return accountMapper.selectOneByName(account.getUsername());
    }

    @Override
    @CacheEvict(key = "#p0")
    public void deleteOneByUsername(String username) {
        accountMapper.deleteOneByUsername(username);
    }
}
