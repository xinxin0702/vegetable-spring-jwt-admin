package com.github.liuzhuoming23.svea.app.service.impl;

import com.github.liuzhuoming23.svea.app.domain.Dict;
import com.github.liuzhuoming23.svea.app.domain.DictItem;
import com.github.liuzhuoming23.svea.app.mapper.DictItemMapper;
import com.github.liuzhuoming23.svea.app.mapper.DictMapper;
import com.github.liuzhuoming23.svea.app.service.DictService;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Dict> list = dictMapper.select(dict);
        for (Dict one : list) {
            List<DictItem> dictItems = dictItemMapper.selectByDictCode(one.getCode());
            one.setItems(dictItems);
        }
        return list;
    }

    @Override
    public Dict selectOneByCode(String code) {
        Dict dict = dictMapper.selectOneByCode(code);
        if (dict == null) {
            throw new SveaException("dict(code=" + code + ") not exist");
        }
        List<DictItem> dictItems = dictItemMapper.selectByDictCode(dict.getCode());
        dict.setItems(dictItems);
        return dict;
    }

    @Override
    public Dict insert(Dict dict) {
        dictMapper.insert(dict);
        return dictMapper.selectOneByCode(dict.getCode());
    }
}
