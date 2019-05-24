package com.github.liuzhuoming23.jwtback.app.controller;

import com.github.liuzhuoming23.jwtback.app.domain.Dict;
import com.github.liuzhuoming23.jwtback.app.domain.DictItem;
import com.github.liuzhuoming23.jwtback.app.service.DictService;
import com.github.liuzhuoming23.jwtback.common.domain.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insert(@Valid Dict dict) {
        dictService.insert(dict);
    }

    @GetMapping
    public Result select(Dict dict) {
        List<Dict> list = dictService.select(dict);
        return new Result().succ(list);
    }

    @GetMapping("{code}")
    public Result selectOneByCode(@PathVariable String code) {
        Dict dict = dictService.selectOneByCode(code);
        return new Result().succ(dict);
    }

    @PostMapping("/{dictId}/item")
    public void insertItem(@Valid DictItem dictItem, @PathVariable Integer dictId) {
        dictItem.setDictId(dictId);
        dictService.insertItem(dictItem);
    }

    @GetMapping("/{dictId}/item")
    public Result insertItem(@PathVariable Integer dictId) {
        List<DictItem> dictItems = dictService.selectByDictId(dictId);
        return new Result().succ(dictItems);
    }

    @GetMapping("/{dictId}/item/{val}")
    public Result insertItem(@PathVariable Integer dictId, @PathVariable Integer val) {
        DictItem dictItem = dictService.selectByDictIdAndVal(dictId, val);
        return new Result().succ(dictItem);
    }
}
