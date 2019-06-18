package com.github.liuzhuoming23.vegetable.admin.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Dict;
import com.github.liuzhuoming23.vegetable.admin.app.domain.DictItem;
import com.github.liuzhuoming23.vegetable.admin.app.mapper.DictItemMapper;
import com.github.liuzhuoming23.vegetable.admin.app.mapper.DictMapper;
import com.github.liuzhuoming23.vegetable.admin.app.service.DictService;
import com.github.liuzhuoming23.vegetable.admin.common.domain.PageParams;
import com.github.liuzhuoming23.vegetable.admin.common.domain.SortParams;
import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典service
 *
 * @author liuzhuoming
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    public List<Dict> select(Dict dict) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        if (dict != null) {
            wrapper.eq(dict.getId() != null, Dict::getId, dict.getId());
            wrapper.eq(StringUtils.isNotEmpty(dict.getCode()), Dict::getCode, dict.getCode());
            wrapper.eq(StringUtils.isNotEmpty(dict.getName()), Dict::getName, dict.getName());
            wrapper.eq(dict.getEnable() != null, Dict::getEnable, dict.getEnable());
        }
        List<Dict> list = dictMapper.selectList(wrapper);
        for (Dict one : list) {
            List<DictItem> dictItems = dictItemMapper.selectListByDictCode(one.getCode());
            one.setItems(dictItems);
        }
        return list;
    }

    @Override
    public IPage<Dict> page(Dict dict, PageParams pageParams, SortParams sortParams) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sortParams.getSortName())) {
            wrapper.orderByDesc("desc".equals(sortParams.getSortOrder()), sortParams.getSortName());
            wrapper.orderByAsc("asc".equals(sortParams.getSortOrder()), sortParams.getSortName());
        }
        if (dict != null) {
            wrapper.lambda().eq(dict.getId() != null, Dict::getId, dict.getId());
            wrapper.lambda()
                .eq(StringUtils.isNotEmpty(dict.getCode()), Dict::getCode, dict.getCode());
            wrapper.lambda()
                .eq(StringUtils.isNotEmpty(dict.getName()), Dict::getName, dict.getName());
            wrapper.lambda().eq(dict.getEnable() != null, Dict::getEnable, dict.getEnable());
        }
        Page<Dict> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        return dictMapper.selectPage(page, wrapper);
    }

    @Override
    public Dict selectOneByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new VsjaException("dict(code=null) not exist");
        }
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getCode, code);
        Dict dict = dictMapper.selectOne(wrapper);
        if (dict == null) {
            throw new VsjaException(String.format("dict(code=%s) not exist", code));
        }
        List<DictItem> dictItems = dictItemMapper.selectListByDictCode(dict.getCode());
        dict.setItems(dictItems);
        return dict;
    }

    @Override
    public void insert(Dict dict) {
        dictMapper.insert(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String code) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getCode, code);
        dictMapper.delete(wrapper);
    }
}
