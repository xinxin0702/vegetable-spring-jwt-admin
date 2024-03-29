package com.github.liuzhuoming23.vegetable.admin.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 角色
 *
 * @author liuzhuoming
 */
@Data
@TableName("role")
public class Role implements Serializable {

    private static final long serialVersionUID = -4022095159465412415L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称
     */
    @NotEmpty
    @Size(min = 3, max = 10)
    @TableField("name")
    private String name;
    /**
     * 是否可用（0是 1否）
     */
    @NotNull
    @Min(0)
    @Max(1)
    @TableField("enable")
    private Integer enable;
}
