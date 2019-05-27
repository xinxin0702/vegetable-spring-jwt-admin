package com.github.liuzhuoming23.svea.app.service.impl;

import com.github.liuzhuoming23.svea.app.domain.LogDetail;
import com.github.liuzhuoming23.svea.app.mapper.LogDetailMapper;
import com.github.liuzhuoming23.svea.app.service.LogDetailService;
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
}
