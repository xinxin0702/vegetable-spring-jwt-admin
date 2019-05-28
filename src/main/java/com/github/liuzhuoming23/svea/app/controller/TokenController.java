package com.github.liuzhuoming23.svea.app.controller;

import static com.github.liuzhuoming23.svea.common.cons.RedisKey.TOKEN_HASH_KEY;

import com.github.liuzhuoming23.svea.app.domain.Account;
import com.github.liuzhuoming23.svea.app.service.AccountService;
import com.github.liuzhuoming23.svea.common.annotation.Log;
import com.github.liuzhuoming23.svea.common.annotation.RequestLimit;
import com.github.liuzhuoming23.svea.common.cons.LogLevel;
import com.github.liuzhuoming23.svea.common.context.AccountContext;
import com.github.liuzhuoming23.svea.common.domain.Result;
import com.github.liuzhuoming23.svea.common.jwt.JwtUtil;
import com.github.liuzhuoming23.svea.common.redis.RedisOperation;
import com.github.liuzhuoming23.svea.util.PswUtil;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 *
 * @author liuzhuoming
 */
@RestController
public class TokenController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisOperation redisOperation;

    @GetMapping("login/{username}")
    @Log(description = "登录系统", level = LogLevel.LV3)
    @RequestLimit
    public Object login(@PathVariable String username, @RequestParam String password) {
        if (accountVerify(username, password)) {
            String jwt = JwtUtil.generateToken(username);
            return new HashMap<String, String>(2) {
                private static final long serialVersionUID = 2214012531832733454L;

                {
                    put("username", username);
                    put("token", jwt);
                }
            };
        } else {
            Result body = new Result()
                .fail(HttpStatus.UNAUTHORIZED, "incorrect username or password", "/login");
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("logout")
    @Log(description = "登出系统", level = LogLevel.LV1)
    public void logout() {
        Account account = AccountContext.get();
        if (account != null && StringUtils.isNotEmpty(account.getUsername())) {
            redisOperation.hash().delete(TOKEN_HASH_KEY, account.getUsername());
        }
    }

    @DeleteMapping("logout/{username}")
    @Log(description = "强制登出系统", level = LogLevel.LV4)
    public void forceLogout(@PathVariable String username) {
        redisOperation.hash().delete(TOKEN_HASH_KEY, username);
    }

    /**
     * 账户验证
     *
     * @param username 用户名
     * @param password 密码
     */
    private boolean accountVerify(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        Account account = accountService.selectOneByUsername(username);
        if (account == null) {
            return false;
        }
        return PswUtil.isEquals(username, password, account.getPassword());
    }
}
