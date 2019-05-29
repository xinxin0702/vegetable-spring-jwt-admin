package com.github.liuzhuoming23.svea.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.liuzhuoming23.svea.app.domain.Dict;
import com.github.liuzhuoming23.svea.app.domain.DictItem;
import com.github.liuzhuoming23.svea.app.mapper.DictItemMapper;
import com.github.liuzhuoming23.svea.app.mapper.DictMapper;
import com.github.liuzhuoming23.svea.app.service.DictService;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
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
            if (dict.getId() != null) {
                wrapper.eq(Dict::getId, dict.getId());
            }
            if (StringUtils.isNotEmpty(dict.getCode())) {
                wrapper.eq(Dict::getCode, dict.getCode());
            }
            if (StringUtils.isNotEmpty(dict.getName())) {
                wrapper.eq(Dict::getName, dict.getName());
            }
            if (dict.getEnable() != null) {
                wrapper.eq(Dict::getEnable, dict.getEnable());
            }
        }
        List<Dict> list = dictMapper.selectList(wrapper);
        for (Dict one : list) {
            List<DictItem> dictItems = dictItemMapper.selectListByDictCode(one.getCode());
            one.setItems(dictItems);
        }
        return list;
    }

    @Override
    public Dict selectOneByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new SveaException("dict(code=null) not exist");
        }
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getCode, code);
        Dict dict = dictMapper.selectOne(wrapper);
        if (dict == null) {
            throw new SveaException(String.format("dict(code=%s) not exist", code));
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
