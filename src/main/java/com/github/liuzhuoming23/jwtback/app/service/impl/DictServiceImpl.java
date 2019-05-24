package com.github.liuzhuoming23.jwtback.app.service.impl;

import com.github.liuzhuoming23.jwtback.app.domain.Dict;
import com.github.liuzhuoming23.jwtback.app.domain.DictItem;
import com.github.liuzhuoming23.jwtback.app.mapper.DictMapper;
import com.github.liuzhuoming23.jwtback.app.service.DictService;
import com.github.liuzhuoming23.jwtback.common.cons.RedisCons;
import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 字典service
 *
 * @author liuzhuoming
 */
@Service
@CacheConfig(cacheNames = RedisCons.CACHE_KEY_DICT_PREFIX)
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
    @Cacheable(key = "#p0")
    public Dict selectOneByCode(String code) {
        Dict dict = dictMapper.selectOneByCode(code);
        if (dict != null) {
            List<DictItem> dictItems = dictMapper.selectByDictCode(dict.getCode());
            dict.setItems(dictItems);
        }
        return dict;
    }

    @Override
    @CachePut(key = "#p0.code")
    public Dict insert(Dict dict) {
        dictMapper.insert(dict);
        return dictMapper.selectOneByCode(dict.getCode());
    }

    @Override
    @Cacheable(key = "'item::' + #p0 + '#' + #p1")
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
    @CachePut(key = "'item::' + #p0.dictId +'#'+ #p0.val")
    public DictItem insertItem(DictItem dictItem) {
        Dict dict = dictMapper.selectOneById(dictItem.getDictId());
        if (dict != null) {
            try {
                dictMapper.insertItem(dictItem);
            } catch (Exception e) {
                throw new JwtbackException("dictItem is exist");
            }
            return dictMapper.selectByDictIdAndVal(dictItem);
        } else {
            throw new JwtbackException("dict not exist");
        }
    }
}
