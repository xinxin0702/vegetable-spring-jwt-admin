package com.github.liuzhuoming23.vegetable.admin.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Account;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Role;
import com.github.liuzhuoming23.vegetable.admin.app.mapper.AccountMapper;
import com.github.liuzhuoming23.vegetable.admin.app.service.AccountService;
import com.github.liuzhuoming23.vegetable.admin.app.service.RoleService;
import com.github.liuzhuoming23.vegetable.admin.common.context.AccountContext;
import com.github.liuzhuoming23.vegetable.admin.common.domain.PageParams;
import com.github.liuzhuoming23.vegetable.admin.common.domain.SortParams;
import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import com.github.liuzhuoming23.vegetable.admin.util.PswUtil;
import com.github.liuzhuoming23.vegetable.admin.util.StringRegexUtil;
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
            throw new VsjaException(
                "username can only contain uppercase or lowercase letters or numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(account.getPassword(), 6, 16)) {
            throw new VsjaException(
                "password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }
        Account list = this.selectOneByUsername(account.getUsername());
        if (list != null) {
            throw new VsjaException(
                String.format("account(username=%s) already exists", account.getUsername()));
        }
        account.setPassword(PswUtil.encrypt(account.getUsername(), account.getPassword()));
        accountMapper.insert(account);
    }

    @Override
    public void update(Account account) {
        if (account == null || account.getId() == null) {
            throw new VsjaException("account(id=null) not exist");
        }
        if (StringUtils.isEmpty(account.getUsername())
            || !account.getUsername().equals(AccountContext.get().getUsername())) {
            throw new VsjaException(String
                .format("operate account(id=%d) not equals current account(id=%d)", account.getId(),
                    AccountContext.get().getId()));
        }
        Account one = accountMapper.selectById(account.getId());
        if (one == null) {
            throw new VsjaException(String.format("account(id=%d) not exist", account.getId()));
        }
        Role role = roleService.selectOneById(account.getRoleId());
        if (role == null) {
            throw new VsjaException(String.format("role(id=%d) not exist", account.getRoleId()));
        }
        account = Account.builder().id(account.getId()).roleId(account.getRoleId())
            .enable(account.getEnable()).isAdmin(account.getIsAdmin()).build();
        accountMapper.updateById(account);
    }

    @Override
    public List<Account> select(Account account) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        if (account != null) {
            wrapper.eq(account.getId() != null, Account::getId, account.getId());
            wrapper.eq(account.getRoleId() != null, Account::getRoleId, account.getRoleId());
            wrapper.eq(StringUtils.isNotEmpty(account.getUsername()), Account::getUsername,
                account.getUsername());
            wrapper.eq(account.getEnable() != null, Account::getEnable, account.getEnable());
            wrapper.eq(account.getIsAdmin() != null, Account::getIsAdmin, account.getIsAdmin());
        }
        return accountMapper.selectList(wrapper);
    }

    @Override
    public IPage<Account> page(Account account, PageParams pageParams, SortParams sortParams) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sortParams.getSortName())) {
            wrapper.orderByDesc("desc".equals(sortParams.getSortOrder()), sortParams.getSortName());
            wrapper.orderByAsc("asc".equals(sortParams.getSortOrder()), sortParams.getSortName());
        }
        if (account != null) {
            wrapper.lambda().eq(account.getId() != null, Account::getId, account.getId());
            wrapper.lambda()
                .eq(account.getRoleId() != null, Account::getRoleId, account.getRoleId());
            wrapper.lambda()
                .eq(StringUtils.isNotEmpty(account.getUsername()), Account::getUsername,
                    account.getUsername());
            wrapper.lambda()
                .eq(account.getIsAdmin() != null, Account::getIsAdmin, account.getIsAdmin());
            wrapper.lambda()
                .eq(account.getEnable() != null, Account::getEnable, account.getEnable());
            wrapper.lambda()
                .between(account.getStartDatetime() != null && account.getEndDatetime() != null,
                    Account::getAddDatetime, account.getStartDatetime(), account.getEndDatetime());
        }
        Page<Account> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        return accountMapper.selectPage(page, wrapper);
    }

    @Override
    public Account selectOneByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new VsjaException(String.format("account(username=%s) not exist", username));
        }
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUsername, username);
        Account account = accountMapper.selectOne(wrapper);
        if (account == null) {
            throw new VsjaException(String.format("account(username=%s) not exist", username));
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
