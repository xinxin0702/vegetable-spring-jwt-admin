package com.github.liuzhuoming23.svea.common.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 分页参数
 *
 * @author liuzhuoming
 */
@Data
public class PageParams implements Serializable {

    private static final long serialVersionUID = 3523201108057234967L;
    /**
     * 页码
     */
    private Integer pageNum = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 10;
}
