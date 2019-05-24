package com.github.liuzhuoming23.jwtback.app.controller;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import com.github.liuzhuoming23.jwtback.app.service.AccountService;
import com.github.liuzhuoming23.jwtback.common.domain.Result;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void insert(@Valid Account account) {
        accountService.insert(account);
    }

    @GetMapping("/{username}")
    public Result selectOneByName(@PathVariable String username) {
        Account account = accountService.selectOneByName(username);
        return new Result().succ(account);
    }

}
