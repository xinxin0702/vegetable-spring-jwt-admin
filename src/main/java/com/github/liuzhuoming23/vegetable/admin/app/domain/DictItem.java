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
 * 字典项
 *
 * @author liuzhuoming
 */
@Data
@TableName("dict_item")
public class DictItem implements Serializable {

    private static final long serialVersionUID = 4525692309395744386L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 所属字典id
     */
    @TableField("dict_id")
    private Integer dictId;
    /**
     * 字典项内容
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    @TableField("context")
    private String context;
    /**
     * 字典项值
     */
    @NotNull
    @Min(0)
    @Max(999)
    @TableField("val")
    private Integer val;
    /**
     * 字典项排序
     */
    @Min(0)
    @Max(999)
    @TableField("sort")
    private Integer sort;
}
