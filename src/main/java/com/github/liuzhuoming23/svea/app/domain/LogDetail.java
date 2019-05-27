package com.github.liuzhuoming23.svea.app.domain;

import java.util.Date;
import lombok.Data;

/**
 * 日志详情
 *
 * @author liuzhuoming
 */
@Data
public class LogDetail {

    private String id;
    /**
     * 日志等级
     */
    private Integer level;
    /**
     * 方法名
     */
    private String method;
    /**
     * 参数
     */
    private String args;
    /**
     * 操作用户id
     */
    private Integer accountId;
    /**
     * 操作用户名称
     */
    private String username;
    /**
     * 日志描述
     */
    private String description;
    /**
     * 方法运行时间
     */
    private Long runTime;
    /**
     * 方法返回值
     */
    private String returnVal;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 添加日期时间
     */
    private Date addDatetime;
}
