package com.github.liuzhuoming23.jwtback.app.service;

import com.github.liuzhuoming23.jwtback.app.domain.Dict;
import com.github.liuzhuoming23.jwtback.app.domain.DictItem;
import com.github.liuzhuoming23.jwtback.app.mapper.DictMapper;
import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
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

    @Override
    public List<Dict> select(Dict dict) {
        List<Dict> list = dictMapper.select(dict);
        for (Dict one : list) {
            List<DictItem> dictItems = dictMapper.selectByDictCode(one.getCode());
            one.setItems(dictItems);
        }
        return list;
    }

    @Override
    public Dict selectOneByCode(String code) {
        Dict dict = dictMapper.selectOneByCode(code);
        if (dict != null) {
            List<DictItem> dictItems = dictMapper.selectByDictCode(dict.getCode());
            dict.setItems(dictItems);
        }
        return dict;
    }

    @Override
    public void insert(Dict dict) {
        dictMapper.insert(dict);
    }

    @Override
    public DictItem selectByDictIdAndVal(Integer dictId, Integer val) {
        DictItem dictItem = new DictItem();
        dictItem.setDictId(dictId);
        dictItem.setVal(val);
        return dictMapper.selectByDictIdAndVal(dictItem);
    }

    @Override
    public List<DictItem> selectByDictId(Integer dictId) {
        return dictMapper.selectByDictId(dictId);
    }

    @Override
    public void insertItem(DictItem dictItem) {
        Dict dict = dictMapper.selectOneById(dictItem.getDictId());
        if (dict != null) {
            dictMapper.insertItem(dictItem);
        } else {
            throw new JwtbackException("dict not exist");
        }
    }
}
