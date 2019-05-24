package com.github.liuzhuoming23.jwtback.app.controller;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import com.github.liuzhuoming23.jwtback.app.service.AccountService;
import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import com.github.liuzhuoming23.jwtback.common.jwt.JwtUtil;
import com.github.liuzhuoming23.jwtback.util.EncryptType;
import com.github.liuzhuoming23.jwtback.util.EncryptUtil;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 *
 * @author liuzhuoming
 */
@RestController
public class LoginController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public Object login(HttpServletResponse response, @Valid Account account) {
        if (accountVerify(account)) {
            String jwt = JwtUtil.generateToken(account.getUsername());
            return new HashMap<String, String>(2) {
                private static final long serialVersionUID = 2214012531832733454L;

                {
                    put("username", account.getUsername());
                    put("token", jwt);
                }
            };
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 账户验证
     *
     * @param account 账户信息
     */
    private boolean accountVerify(Account account) {
        Account exist = accountService.selectOneByName(account.getUsername());
        if (exist == null) {
            return false;
        }
        if (!EncryptUtil.encode(account.getUsername() + account.getPassword(), EncryptType.MD5)
            .equals(exist.getPassword())) {
            throw new JwtbackException("incorrect username or password");
        }
        return true;
    }
}
