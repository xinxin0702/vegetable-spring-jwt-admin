package com.github.liuzhuoming23.svea.app.domain;

import java.util.List;
import lombok.Data;

/**
 * 角色信息
 *
 * @author liuzhuoming
 */
@Data
public class Role {

    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 是否可用（0是 1否）
     */
    private Integer enable;
    /**
     * 菜单集合
     */
    private transient List<Menu> menus;
}
