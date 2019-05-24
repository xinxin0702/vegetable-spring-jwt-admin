package com.github.liuzhuoming23.jwtback.app.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 字典内容
 *
 * @author liuzhuoming
 */
@Data
public class DictItem {

    private Integer id;
    private Integer dictId;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String context;
    @NotNull
    private Integer val;
    private Integer sort;
}
