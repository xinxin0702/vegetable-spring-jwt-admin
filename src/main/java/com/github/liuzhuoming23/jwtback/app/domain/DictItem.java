package com.github.liuzhuoming23.jwtback.app.domain;

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
    private Integer dictId;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String context;
    @NotNull
    @Min(0)
    @Max(999)
    private Integer val;
    private Integer sort;
}
