package com.github.liuzhuoming23.vegetable.admin.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Account;
import com.github.liuzhuoming23.vegetable.admin.app.service.AccountService;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.Log;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.RequestLimit;
import com.github.liuzhuoming23.vegetable.admin.common.cons.LogLevel;
import com.github.liuzhuoming23.vegetable.admin.common.domain.PageParams;
import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import com.github.liuzhuoming23.vegetable.admin.common.domain.SortParams;
import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import com.github.liuzhuoming23.vegetable.admin.util.PswUtil;
import com.github.liuzhuoming23.vegetable.admin.util.StringRegexUtil;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    @Log(description = "添加账户", level = LogLevel.LV5)
    public void insert(@Valid Account account) {
        if (StringUtils.isEmpty(account.getPassword())) {
            throw new VsjaException("password must not be null or empty");
        }
        accountService.insert(account);
    }

    @PutMapping("{username}")
    @Log(description = "更新账户", level = LogLevel.LV5)
    public void update(@Valid Account account, @PathVariable String username) {
        account.setUsername(username);
        accountService.update(account);
    }

    @GetMapping
    @Log(description = "查看账户分页列表", level = LogLevel.LV2)
    public Result select(Account account, PageParams pageParams, SortParams sortParams) {
        IPage<Account> page = accountService.page(account, pageParams, sortParams);
        return new Result().succ(page);
    }

    @GetMapping("{username}")
    @Log(description = "查看账户", level = LogLevel.LV2)
    public Result selectOneByName(@PathVariable String username) {
        Account account = accountService.selectOneByUsername(username);
        account.setPassword("******");
        return new Result().succ(account);
    }

    @PutMapping("{username}/psw")
    @Log(description = "修改账户密码", level = LogLevel.LV5)
    @RequestLimit(count = 1, interval = 24 * 60 * 60 * 1000)
    public void updatePasswordByUsername(@PathVariable String username,
        @RequestParam String password, @RequestParam String newPassword) {
        if (!StringRegexUtil.isContainLetterOrDigit(username, 6, 16)) {
            throw new VsjaException(
                "username can only contain uppercase or lowercase letters or numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(password, 6, 16)) {
            throw new VsjaException(
                "old password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(newPassword, 6, 16)) {
            throw new VsjaException(
                "new password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }

        Account one = accountService.selectOneByUsername(username);

        if (one == null) {
            throw new VsjaException("account not exist");
        }

        if (PswUtil.isEquals(username, password, one.getPassword())) {
            accountService.updatePasswordByUsername(username, newPassword);
        } else {
            throw new VsjaException("incorrect username or password");
        }
    }

    @DeleteMapping("{username}")
    @Log(description = "删除账户", level = LogLevel.LV5)
    public void deleteOneByUsername(@PathVariable String username) {
        accountService.deleteOneByUsername(username);
    }
}
