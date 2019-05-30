package com.github.liuzhuoming23.svea.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.liuzhuoming23.svea.app.domain.LogDetail;
import com.github.liuzhuoming23.svea.common.domain.PageParams;
import com.github.liuzhuoming23.svea.common.domain.SortParams;

/**
 * 日志详情service
 *
 * @author liuzhuoming
 */
public interface LogDetailService {

    /**
     * 添加日志记录
     *
     * @param logDetail 日志详情
     */
    void insert(LogDetail logDetail);

    /**
     * 获取分页日志详情列表
     *
     * @param logDetail 日志详情查询
     * @param pageParams 分页参数
     * @param sortParams 排序参数
     */
    IPage<LogDetail> page(LogDetail logDetail, PageParams pageParams, SortParams sortParams);
}
