package com.github.liuzhuoming23.svea.app.domain;

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
public class DictItem {

    private Integer id;
    /**
     * 所属字典id
     */
    private Integer dictId;
    /**
     * 字典项内容
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    private String context;
    /**
     * 字典项值
     */
    @NotNull
    @Min(0)
    @Max(999)
    private Integer val;
    /**
     * 字典项排序
     */
    private Integer sort;
}
