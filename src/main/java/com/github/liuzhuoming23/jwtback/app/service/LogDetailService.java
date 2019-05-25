package com.github.liuzhuoming23.jwtback.app.service;

import com.github.liuzhuoming23.jwtback.app.domain.LogDetail;

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
}
