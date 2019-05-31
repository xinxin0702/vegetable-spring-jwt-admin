package com.github.liuzhuoming23.vegetable.admin.app.controller;

import com.github.liuzhuoming23.vegetable.admin.app.domain.Account;
import com.github.liuzhuoming23.vegetable.admin.app.service.AccountService;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.Log;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.RequestLimit;
import com.github.liuzhuoming23.vegetable.admin.common.cons.LogLevel;
import com.github.liuzhuoming23.vegetable.admin.common.context.AccountContext;
import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import com.github.liuzhuoming23.vegetable.admin.common.jwt.JwtUtil;
import com.github.liuzhuoming23.vegetable.admin.common.redis.RedisOperation;
import com.github.liuzhuoming23.vegetable.admin.util.PswUtil;
import com.github.liuzhuoming23.vegetable.admin.common.cons.RedisKey;
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
                .fail(HttpStatus.UNAUTHORIZED, "incorrect username or password or not enable",
                    "/login");
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("logout")
    @Log(description = "登出系统", level = LogLevel.LV1)
    public void logout() {
        Account account = AccountContext.get();
        if (account != null && StringUtils.isNotEmpty(account.getUsername())) {
            redisOperation.delete(RedisKey.TOKEN_HASH_KEY + "::" + account.getUsername());
        }
    }

    @DeleteMapping("logout/{username}")
    @Log(description = "强制登出系统", level = LogLevel.LV4)
    public void forceLogout(@PathVariable String username) {
        redisOperation.delete(RedisKey.TOKEN_HASH_KEY + "::" + username);
    }

    /**
     * 账户及密码验证
     *
     * @param username 用户名
     * @param password 密码
     */
    private boolean accountVerify(String username, String password) {
        //验证参数是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        Account account = accountService.selectOneByUsername(username);
        //验证账号是否存在
        if (account == null) {
            return false;
        }
        //验证账号是否启用
        if (account.getEnable() != 0) {
            return false;
        }
        //验证密码是否匹配
        return PswUtil.isEquals(username, password, account.getPassword());
    }
}
