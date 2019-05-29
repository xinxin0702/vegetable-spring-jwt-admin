package com.github.liuzhuoming23.svea.app.service.impl;

import static com.github.liuzhuoming23.svea.common.cons.RedisKey.TOKEN_HASH_KEY;

import com.github.liuzhuoming23.svea.app.domain.Account;
import com.github.liuzhuoming23.svea.app.mapper.AccountMapper;
import com.github.liuzhuoming23.svea.app.service.AccountService;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
import com.github.liuzhuoming23.svea.common.redis.RedisOperation;
import com.github.liuzhuoming23.svea.util.PswUtil;
import com.github.liuzhuoming23.svea.util.StringRegexUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户service
 *
 * @author liuzhuoming
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RedisOperation redisOperation;

    @Override
    public void insert(Account account) {
        if (!StringRegexUtil.isContainLetterOrDigit(account.getUsername(), 6, 16)) {
            throw new SveaException(
                "username can only contain uppercase or lowercase letters or numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(account.getPassword(), 6, 16)) {
            throw new SveaException(
                "password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }
        Account list = accountMapper.selectOneByUsername(account.getUsername());
        if (list != null) {
            throw new SveaException(
                "account(username=" + account.getUsername() + ") already exists");
        }
        account.setPassword(PswUtil.encrypt(account.getUsername(), account.getPassword()));
        accountMapper.insert(account);
    }

    @Override
    public List<Account> select(Account account) {
        return accountMapper.select(account);
    }

    @Override
    public Account selectOneByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new SveaException("account(username=" + username + ") not exist");
        }
        Account account = accountMapper.selectOneByUsername(username);
        if (account == null) {
            throw new SveaException("account(username=" + username + ") not exist");
        }
        return account;
    }

    @Override
    public void updatePasswordByUsername(Account account) {
        account.setPassword(PswUtil.encrypt(account.getUsername(), account.getPassword()));
        accountMapper.updatePasswordByUsername(account);
    }

    @Override
    public void deleteOneByUsername(String username) {
        accountMapper.deleteOneByUsername(username);
        redisOperation.delete(TOKEN_HASH_KEY + "::" + username);
    }
}
