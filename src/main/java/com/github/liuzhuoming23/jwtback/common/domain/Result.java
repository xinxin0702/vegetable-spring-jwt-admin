package com.github.liuzhuoming23.jwtback.common.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;

/**
 * 返回值
 *
 * @author liuzhuoming
 */
public class Result extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 2872144685252722112L;

    public Result fail(String msg) {
        super.put("timestamp", LocalDateTime.now());
        super.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        super.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        super.put("message", msg);
        super.put("path", "");
        return this;
    }

    public Result fail(String msg, String path) {
        super.put("timestamp", LocalDateTime.now());
        super.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        super.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        super.put("message", msg);
        super.put("path", path);
        return this;
    }

    public Result fail(HttpStatus httpStatus, String msg, String path) {
        super.put("timestamp", LocalDateTime.now());
        super.put("status", httpStatus.value());
        super.put("error", httpStatus.getReasonPhrase());
        super.put("message", msg);
        super.put("path", path);
        return this;
    }

    public Result succ(Object data) {
        super.put("data", data);
        return this;
    }

    /**
     * 禁止主动添加元素
     */
    @Override
    public Object put(String key, Object value) {
        return null;
    }
}
