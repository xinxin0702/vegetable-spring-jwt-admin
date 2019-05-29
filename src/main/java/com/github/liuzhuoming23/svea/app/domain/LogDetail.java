package com.github.liuzhuoming23.svea.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import lombok.Data;

/**
 * 日志详情
 *
 * @author liuzhuoming
 */
@Data
public class LogDetail {

    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 日志等级
     */
    @TableField("level")
    private Integer level;
    /**
     * 方法名
     */
    @TableField("method")
    private String method;
    /**
     * 参数
     */
    @TableField("args")
    private String args;
    /**
     * 操作用户id
     */
    @TableField("account_id")
    private Integer accountId;
    /**
     * 操作用户名称
     */
    @TableField("username")
    private String username;
    /**
     * 日志描述
     */
    @TableField("description")
    private String description;
    /**
     * 方法运行时间
     */
    @TableField("run_time")
    private Long runTime;
    /**
     * 方法返回值
     */
    @TableField("return_val")
    private String returnVal;
    /**
     * 异常信息
     */
    @TableField("exception")
    private String exception;
    /**
     * 添加日期时间
     */
    @TableField("add_datetime")
    private Date addDatetime;
}
