package com.github.liuzhuoming23.jwtback.app.controller;

import com.github.liuzhuoming23.jwtback.common.context.AccountContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String test() {
        return AccountContext.get().toString();
    }
}
