package com.github.liuzhuoming23.vegetable.admin.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户
 *
 * @author liuzhuoming
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("account")
public class Account implements Serializable {

    private static final long serialVersionUID = 5160320381135058949L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    @NotEmpty
    @Size(min = 6, max = 16)
    @TableField("username")
    private String username;
    /**
     * 密码
     */
    @Size(min = 6, max = 16)
    @TableField("password")
    private String password;
    /**
     * 角色id
     */
    @NotNull
    @Min(0)
    @TableField("role_id")
    private Integer roleId;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("add_datetime")
    private Date addDatetime;
    /**
     * 是否可用（0是 1否）
     */
    @NotNull
    @Min(0)
    @Max(1)
    @TableField("enable")
    private Integer enable;
    /**
     * 是否超级管理员（0否 1是）
     */
    @NotNull
    @Min(0)
    @Max(1)
    @TableField("is_admin")
    private Integer isAdmin;

    private transient Date startDatetime;
    private transient Date endDatetime;
}
