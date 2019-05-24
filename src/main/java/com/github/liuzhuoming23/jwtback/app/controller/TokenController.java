package com.github.liuzhuoming23.jwtback.app.controller;

import static com.github.liuzhuoming23.jwtback.common.cons.RedisCons.TOKEN_HASH_KEY;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import com.github.liuzhuoming23.jwtback.app.service.AccountService;
import com.github.liuzhuoming23.jwtback.common.domain.Result;
import com.github.liuzhuoming23.jwtback.common.jwt.JwtUtil;
import com.github.liuzhuoming23.jwtback.common.redis.RedisOperation;
import com.github.liuzhuoming23.jwtback.util.EncryptType;
import com.github.liuzhuoming23.jwtback.util.EncryptUtil;
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

    @DeleteMapping("logout/{username}")
    public void logout(@PathVariable String username) {
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
        Account exist = accountService.selectOneByName(username);
        if (exist == null) {
            return false;
        }
        if (!EncryptUtil.encode(username + password, EncryptType.MD5)
            .equals(exist.getPassword())) {
            return false;
        }
        return true;
    }
}
