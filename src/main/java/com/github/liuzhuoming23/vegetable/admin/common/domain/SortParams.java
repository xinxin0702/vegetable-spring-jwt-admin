package com.github.liuzhuoming23.vegetable.admin.common.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 排序参数
 *
 * @author liuzhuoming
 */
@Data
public class SortParams implements Serializable {

    private static final long serialVersionUID = -525873793608037662L;
    /**
     * 排序字段
     */
    private String sortName;
    /**
     * 排序方式（desc：降序，asc：升序）
     */
    private String sortOrder = "asc";
}
