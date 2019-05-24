package com.github.liuzhuoming23.jwtback.app.controller;

import static com.github.liuzhuoming23.jwtback.common.cons.RedisCons.TOKEN_HASH_KEY;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import com.github.liuzhuoming23.jwtback.app.service.AccountService;
import com.github.liuzhuoming23.jwtback.common.domain.Result;
import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import com.github.liuzhuoming23.jwtback.common.redis.RedisOperation;
import com.github.liuzhuoming23.jwtback.util.PswUtil;
import com.github.liuzhuoming23.jwtback.util.StringRegexUtil;
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
    @Autowired
    private RedisOperation redisOperation;

    @PostMapping
    public void insert(@Valid @ModelAttribute Account account) {
        accountService.insert(account);
    }

    @GetMapping
    public Result select(Account account) {
        List<Account> accounts = accountService.select(account);
        return new Result().succ(accounts);
    }

    @GetMapping("/{username}")
    public Result selectOneByName(@PathVariable String username) {
        Account account = accountService.selectOneByUsername(username);
        account.setPassword("******");
        return new Result().succ(account);
    }

    @PutMapping("/{username}/psw")
    public void updatePasswordByUsername(@PathVariable String username,
        @RequestParam String password, @RequestParam String newPassword) {
        if (!StringRegexUtil.isContainLetterOrDigit(username, 6, 16)) {
            throw new JwtbackException(
                "username can only contain uppercase or lowercase letters or numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(password, 6, 16)) {
            throw new JwtbackException(
                "old password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }
        if (!StringRegexUtil.isContainUppercaseAndLowercaseAndDigit(newPassword, 6, 16)) {
            throw new JwtbackException(
                "new password must contain uppercase and lowercase letters and numbers and length between 6 and 16");
        }

        Account one = accountService.selectOneByUsername(username);

        if (one == null) {
            throw new JwtbackException("account not exist");
        }

        if (PswUtil.valid(username, password, one.getPassword())) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(newPassword);
            accountService.updatePasswordByUsername(account);
        } else {
            throw new JwtbackException("incorrect username or password");
        }
    }

    @DeleteMapping("/{username}")
    public void deleteOneByUsername(@PathVariable String username) {
        accountService.deleteOneByUsername(username);
        redisOperation.hash().delete(TOKEN_HASH_KEY, username);
    }
}
