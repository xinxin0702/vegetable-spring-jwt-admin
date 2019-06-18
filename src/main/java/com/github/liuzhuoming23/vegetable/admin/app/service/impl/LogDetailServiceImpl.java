package com.github.liuzhuoming23.vegetable.admin.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.liuzhuoming23.vegetable.admin.app.domain.LogDetail;
import com.github.liuzhuoming23.vegetable.admin.app.mapper.LogDetailMapper;
import com.github.liuzhuoming23.vegetable.admin.app.service.LogDetailService;
import com.github.liuzhuoming23.vegetable.admin.common.domain.PageParams;
import com.github.liuzhuoming23.vegetable.admin.common.domain.SortParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 日志详情service
 *
 * @author liuzhuoming
 */
@Service
public class LogDetailServiceImpl implements LogDetailService {

    @Autowired
    private LogDetailMapper logDetailMapper;

    @Override
    public void insert(LogDetail logDetail) {
        logDetailMapper.insert(logDetail);
    }

    @Override
    public IPage<LogDetail> page(LogDetail logDetail, PageParams pageParams,
        SortParams sortParams) {
        QueryWrapper<LogDetail> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sortParams.getSortName())) {
            wrapper.orderByDesc("desc".equals(sortParams.getSortOrder()), sortParams.getSortName());
            wrapper.orderByAsc("asc".equals(sortParams.getSortOrder()), sortParams.getSortName());
        }
        if (logDetail != null) {
            wrapper.lambda().eq(logDetail.getId() != null, LogDetail::getId, logDetail.getId());
            wrapper.lambda()
                .eq(logDetail.getLevel() != null, LogDetail::getLevel, logDetail.getLevel());
            wrapper.lambda()
                .eq(StringUtils.isNotEmpty(logDetail.getMethod()), LogDetail::getMethod,
                    logDetail.getMethod());
            wrapper.lambda().eq(logDetail.getAccountId() != null, LogDetail::getAccountId,
                logDetail.getAccountId());
            wrapper.lambda()
                .eq(StringUtils.isNotEmpty(logDetail.getUsername()), LogDetail::getUsername,
                    logDetail.getUsername());
            wrapper.lambda().eq(StringUtils.isNotEmpty(logDetail.getDescription()),
                LogDetail::getDescription, logDetail.getDescription());
            wrapper.lambda().between(
                logDetail.getStartDatetime() != null && logDetail.getEndDatetime() != null,
                LogDetail::getAddDatetime, logDetail.getStartDatetime(),
                logDetail.getEndDatetime());
        }
        Page<LogDetail> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        return logDetailMapper.selectPage(page, wrapper);
    }
}
