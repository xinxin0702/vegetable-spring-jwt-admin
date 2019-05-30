package com.github.liuzhuoming23.svea.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.liuzhuoming23.svea.app.domain.Account;
import com.github.liuzhuoming23.svea.app.domain.Role;
import com.github.liuzhuoming23.svea.app.mapper.AccountMapper;
import com.github.liuzhuoming23.svea.app.service.AccountService;
import com.github.liuzhuoming23.svea.app.service.RoleService;
import com.github.liuzhuoming23.svea.common.context.AccountContext;
import com.github.liuzhuoming23.svea.common.domain.PageParams;
import com.github.liuzhuoming23.svea.common.domain.SortParams;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
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
    private RoleService roleService;

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
        Account list = this.selectOneByUsername(account.getUsername());
        if (list != null) {
            throw new SveaException(
                String.format("account(username=%s) already exists", account.getUsername()));
        }
        account.setPassword(PswUtil.encrypt(account.getUsername(), account.getPassword()));
        accountMapper.insert(account);
    }

    @Override
    public void update(Account account) {
        if (account == null || account.getId() == null) {
            throw new SveaException("account(id=null) not exist");
        }
        if (StringUtils.isEmpty(account.getUsername())
            || !account.getUsername().equals(AccountContext.get().getUsername())) {
            throw new SveaException(String
                .format("operate account(id=%d) not equals current account(id=%d)", account.getId(),
                    AccountContext.get().getId()));
        }
        Account one = accountMapper.selectById(account.getId());
        if (one == null) {
            throw new SveaException(String.format("account(id=%d) not exist", account.getId()));
        }
        Role role = roleService.selectOneById(account.getRoleId());
        if (role == null) {
            throw new SveaException(String.format("role(id=%d) not exist", account.getRoleId()));
        }
        account = Account.builder().id(account.getId()).roleId(account.getRoleId())
            .enable(account.getEnable()).isAdmin(account.getIsAdmin()).build();
        accountMapper.updateById(account);
    }

    @Override
    public List<Account> select(Account account) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        if (account != null) {
            if (account.getId() != null) {
                wrapper.eq(Account::getId, account.getId());
            }
            if (account.getRoleId() != null) {
                wrapper.eq(Account::getRoleId, account.getRoleId());
            }
            if (StringUtils.isNotEmpty(account.getUsername())) {
                wrapper.eq(Account::getUsername, account.getUsername());
            }
            if (account.getEnable() != null) {
                wrapper.eq(Account::getEnable, account.getEnable());
            }
            if (account.getIsAdmin() != null) {
                wrapper.eq(Account::getIsAdmin, account.getIsAdmin());
            }
        }
        return accountMapper.selectList(wrapper);
    }

    @Override
    public IPage<Account> page(Account account, PageParams pageParams, SortParams sortParams) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sortParams.getSortName())) {
            if ("desc".equals(sortParams.getSortOrder())) {
                wrapper.orderByDesc(sortParams.getSortName());
            } else if (("asc".equals(sortParams.getSortOrder()))) {
                wrapper.orderByAsc(sortParams.getSortName());
            }
        }
        if (account != null) {
            if (account.getId() != null) {
                wrapper.lambda().eq(Account::getId, account.getId());
            }
            if (account.getRoleId() != null) {
                wrapper.lambda().eq(Account::getRoleId, account.getRoleId());
            }
            if (StringUtils.isNotEmpty(account.getUsername())) {
                wrapper.lambda().eq(Account::getUsername, account.getUsername());
            }
            if (account.getIsAdmin() != null) {
                wrapper.lambda().eq(Account::getIsAdmin, account.getIsAdmin());
            }
            if (account.getEnable() != null) {
                wrapper.lambda().eq(Account::getEnable, account.getEnable());
            }
            if (account.getStartDatetime() != null && account.getEndDatetime() != null) {
                wrapper.lambda().between(Account::getAddDatetime, account.getStartDatetime(),
                    account.getEndDatetime());
            }
        }
        Page<Account> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        return accountMapper.selectPage(page, wrapper);
    }

    @Override
    public Account selectOneByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new SveaException(String.format("account(username=%s) not exist", username));
        }
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUsername, username);
        Account account = accountMapper.selectOne(wrapper);
        if (account == null) {
            throw new SveaException(String.format("account(username=%s) not exist", username));
        }
        return account;
    }

    @Override
    public void updatePasswordByUsername(String username, String password) {
        Account account = new Account();
        account.setPassword(PswUtil.encrypt(username, password));
        LambdaUpdateWrapper<Account> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Account::getUsername, username);
        accountMapper.update(account, wrapper);
    }

    @Override
    public void deleteOneByUsername(String username) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUsername, username);
        accountMapper.delete(wrapper);
    }
}
