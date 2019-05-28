package com.github.liuzhuoming23.svea.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 账户信息
 *
 * @author liuzhuoming
 */
@Data
public class Account {

    private Integer id;
    /**
     * 用户名
     */
    @NotEmpty
    @Size(min = 6, max = 16)
    private String username;
    /**
     * 密码
     */
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;
    /**
     * 角色id
     */
    @NotNull
    @Min(0)
    private Integer roleId;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addDatetime;
    /**
     * 是否可用（0是 1否）
     */
    private Integer enable;
    /**
     * 是否超级管理员（0否 1是）
     */
    private Integer isAdmin;
    /**
     * 角色集合
     */
    private transient List<Role> roles;
    /**
     * 菜单集合
     */
    private transient List<Menu> menus;
}
