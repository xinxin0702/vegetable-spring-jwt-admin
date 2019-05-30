package com.github.liuzhuoming23.svea.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.liuzhuoming23.svea.app.domain.LogDetail;
import com.github.liuzhuoming23.svea.app.mapper.LogDetailMapper;
import com.github.liuzhuoming23.svea.app.service.LogDetailService;
import com.github.liuzhuoming23.svea.common.domain.PageParams;
import com.github.liuzhuoming23.svea.common.domain.SortParams;
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
            if ("desc".equals(sortParams.getSortOrder())) {
                wrapper.orderByDesc(sortParams.getSortName());
            } else if (("asc".equals(sortParams.getSortOrder()))) {
                wrapper.orderByAsc(sortParams.getSortName());
            }
        }
        if (logDetail != null) {
            if (logDetail.getId() != null) {
                wrapper.lambda().eq(LogDetail::getId, logDetail.getId());
            }
            if (logDetail.getLevel() != null) {
                wrapper.lambda().eq(LogDetail::getLevel, logDetail.getLevel());
            }
            if (StringUtils.isNotEmpty(logDetail.getMethod())) {
                wrapper.lambda().eq(LogDetail::getMethod, logDetail.getMethod());
            }
            if (logDetail.getAccountId() != null) {
                wrapper.lambda().eq(LogDetail::getAccountId, logDetail.getAccountId());
            }
            if (StringUtils.isNotEmpty(logDetail.getUsername())) {
                wrapper.lambda().eq(LogDetail::getUsername, logDetail.getUsername());
            }
            if (StringUtils.isNotEmpty(logDetail.getDescription())) {
                wrapper.lambda().eq(LogDetail::getDescription, logDetail.getDescription());
            }

            if (logDetail.getStartDatetime() != null && logDetail.getEndDatetime() != null) {
                wrapper.lambda().between(LogDetail::getAddDatetime, logDetail.getStartDatetime(),
                    logDetail.getEndDatetime());
            }
        }
        Page<LogDetail> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        return logDetailMapper.selectPage(page, wrapper);
    }
}
