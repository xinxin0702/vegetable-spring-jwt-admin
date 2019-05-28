package com.github.liuzhuoming23.svea.app.controller;

import com.github.liuzhuoming23.svea.app.domain.Account;
import com.github.liuzhuoming23.svea.app.service.AccountService;
import com.github.liuzhuoming23.svea.common.annotation.Log;
import com.github.liuzhuoming23.svea.common.cons.LogLevel;
import com.github.liuzhuoming23.svea.common.domain.Result;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
import com.github.liuzhuoming23.svea.util.PswUtil;
import com.github.liuzhuoming23.svea.util.StringRegexUtil;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public void insert(@Valid @ModelAttribute Account account) {
        accountService.insert(account);
    }

    @GetMapping
    @Log(description = "查看账户列表", level = LogLevel.LV2)
    public Result select(Account account) {
        List<Account> accounts = accountService.select(account);
        return new Result().succ(accounts);
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
    public void updatePasswordByUsername(@PathVariable String username,
        @RequestParam String password, @RequestParam String newPassword) {
        if (!StringRegexUtil.isContainLetterOrDigit(username, 6, 16)) {
            throw new SveaException(
                "username can only contain uppercase or lowercase letters or numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(password, 6, 16)) {
            throw new SveaException(
                "old password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(newPassword, 6, 16)) {
            throw new SveaException(
                "new password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }

        Account one = accountService.selectOneByUsername(username);

        if (one == null) {
            throw new SveaException("account not exist");
        }

        if (PswUtil.isEquals(username, password, one.getPassword())) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(newPassword);
            accountService.updatePasswordByUsername(account);
        } else {
            throw new SveaException("incorrect username or password");
        }
    }

    @DeleteMapping("{username}")
    @Log(description = "删除账户", level = LogLevel.LV5)
    public void deleteOneByUsername(@PathVariable String username) {
        accountService.deleteOneByUsername(username);
    }
}
