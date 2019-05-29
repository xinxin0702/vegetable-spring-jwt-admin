package com.github.liuzhuoming23.svea.app.domain;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 菜单
 *
 * @author liuzhuoming
 */
@Data
public class Menu {

    private Integer id;
    /**
     * 菜单名称
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;
    /**
     * 父菜单id
     */
    @Min(0)
    private Integer parentId;
    /**
     * 菜单排序
     */
    private Integer sort;
    /**
     * 是否可用（0是 1否）
     */
    @NotNull
    private Integer enable;
    /**
     * 子菜单集合
     */
    private transient List<Menu> children;
}
