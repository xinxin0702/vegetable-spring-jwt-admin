package com.github.liuzhuoming23.svea.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 字典
 *
 * @author liuzhuoming
 */
@Data
@TableName("dict")
public class Dict {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 字典编码
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    @TableField("code")
    private String code;
    /**
     * 字典名称
     */
    @NotEmpty
    @Size(min = 1, max = 20)
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
    /**
     * 字典项集合
     */
    private transient List<DictItem> items;
}
