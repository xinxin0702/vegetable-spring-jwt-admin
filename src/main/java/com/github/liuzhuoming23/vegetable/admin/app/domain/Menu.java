package com.github.liuzhuoming23.vegetable.admin.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Max;
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
@TableName("menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 7373738306307209895L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单名称
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    @TableField("name")
    private String name;
    /**
     * 父菜单id
     */
    @Min(0)
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 菜单排序
     */
    @Min(0)
    @Max(999)
    @TableField("sort")
    private Integer sort;
    /**
     * 是否可用（0是 1否）
     */
    @NotNull
    @Min(0)
    @Max(1)
    @TableField("enable")
    private Integer enable;
    /**
     * 子菜单集合
     */
    private transient List<Menu> children;
}
