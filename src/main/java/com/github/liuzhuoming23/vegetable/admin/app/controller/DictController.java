package com.github.liuzhuoming23.vegetable.admin.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Dict;
import com.github.liuzhuoming23.vegetable.admin.app.service.DictService;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.Log;
import com.github.liuzhuoming23.vegetable.admin.common.domain.PageParams;
import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import com.github.liuzhuoming23.vegetable.admin.common.domain.SortParams;
import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping
    @Log(description = "添加字典")
    public void insert(@Valid Dict dict) {
        try {
            dictService.insert(dict);
        } catch (Exception e) {
            throw new VsjaException("insert dict failed");
        }
    }

    @GetMapping
    @Log(description = "获取字典分页列表")
    public Result page(Dict dict, PageParams pageParams, SortParams sortParams) {
        IPage<Dict> page = dictService.page(dict, pageParams, sortParams);
        return new Result().succ(page);
    }

    @GetMapping("{code}")
    @Log(description = "获取字典")
    public Result selectOneByCode(@PathVariable String code) {
        Dict dict = dictService.selectOneByCode(code);
        return new Result().succ(dict);
    }

    @DeleteMapping("{code}")
    @Log(description = "删除字典")
    public void delete(@PathVariable String code) {
        dictService.delete(code);
    }
}
