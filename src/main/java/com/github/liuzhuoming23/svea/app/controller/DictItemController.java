package com.github.liuzhuoming23.svea.app.controller;

import com.github.liuzhuoming23.svea.app.domain.DictItem;
import com.github.liuzhuoming23.svea.app.service.DictItemService;
import com.github.liuzhuoming23.svea.common.annotation.Log;
import com.github.liuzhuoming23.svea.common.domain.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典项controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("dict")
public class DictItemController {

    @Autowired
    private DictItemService dictItemService;

    @PostMapping("{dictId}/item")
    @Log(description = "添加字典项")
    public void insertItem(@Valid DictItem dictItem, @PathVariable Integer dictId) {
        dictItem.setDictId(dictId);
        dictItemService.insert(dictItem);
    }

    @GetMapping("{dictId}/item")
    @Log(description = "获取字典项集合")
    public Result selectItem(@PathVariable Integer dictId) {
        List<DictItem> dictItems = dictItemService.selectByDictId(dictId);
        return new Result().succ(dictItems);
    }

    @GetMapping("{dictId}/item/{val}")
    @Log(description = "获取字典项")
    public Result insertItem(@PathVariable Integer dictId, @PathVariable Integer val) {
        DictItem dictItem = dictItemService.selectByDictIdAndDictItemVal(dictId, val);
        return new Result().succ(dictItem);
    }
}
