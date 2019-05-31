package com.github.liuzhuoming23.vegetable.admin.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.liuzhuoming23.vegetable.admin.app.domain.LogDetail;
import com.github.liuzhuoming23.vegetable.admin.app.service.LogDetailService;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.Log;
import com.github.liuzhuoming23.vegetable.admin.common.cons.LogLevel;
import com.github.liuzhuoming23.vegetable.admin.common.domain.PageParams;
import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import com.github.liuzhuoming23.vegetable.admin.common.domain.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志详情controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("logDetail")
public class LogDetailController {

    @Autowired
    private LogDetailService logDetailService;

    @GetMapping
    @Log(description = "查看日志详情分页列表", level = LogLevel.LV2)
    public Result select(LogDetail logDetail, PageParams pageParams, SortParams sortParams) {
        IPage<LogDetail> page = logDetailService.page(logDetail, pageParams, sortParams);
        return new Result().succ(page);
    }
}
