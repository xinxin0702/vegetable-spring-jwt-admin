package com.github.liuzhuoming23.svea.app.domain;

import java.util.List;
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
public class Dict {

    private Integer id;
    /**
     * 字典编码
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    private String code;
    /**
     * 字典名称
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;
    /**
     * 字典项集合
     */
    private List<DictItem> items;
    /**
     * 是否可用（0是 1否）
     */
    @NotNull
    private Integer enable;
}
