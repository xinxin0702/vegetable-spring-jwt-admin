package com.github.liuzhuoming23.svea.app.domain;

import java.util.List;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    @Size(min = 1, max = 20)
    private String code;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;
    private List<DictItem> items;
    private Integer enable;
}
