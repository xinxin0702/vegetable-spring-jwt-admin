package com.github.liuzhuoming23.svea.app.domain;

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
public class Role {

    private Integer id;
    /**
     * 角色名称
     */
    @NotEmpty
    @Size(min = 3, max = 10)
    private String name;
    /**
     * 是否可用（0是 1否）
     */
    @NotNull
    private Integer enable;
}
