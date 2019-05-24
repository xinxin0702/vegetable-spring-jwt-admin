package com.github.liuzhuoming23.jwtback.common.domain;

import java.util.HashMap;

/**
 * 返回值
 *
 * @author liuzhuoming
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 2872144685252722112L;

    public Result fail(String msg) {
        put("msg", msg);
        return this;
    }

    public Result succ(Object data) {
        put("data", data);
        return this;
    }
}
