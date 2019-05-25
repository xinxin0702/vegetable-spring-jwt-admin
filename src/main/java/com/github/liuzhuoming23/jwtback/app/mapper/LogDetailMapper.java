package com.github.liuzhuoming23.jwtback.app.mapper;

import com.github.liuzhuoming23.jwtback.app.domain.LogDetail;

/**
 * 日志详情mapper
 *
 * @author liuzhuoming
 */
public interface LogDetailMapper {

    /**
     * 添加日志记录
     *
     * @param logDetail 日志详情
     */
    void insert(LogDetail logDetail);
}
