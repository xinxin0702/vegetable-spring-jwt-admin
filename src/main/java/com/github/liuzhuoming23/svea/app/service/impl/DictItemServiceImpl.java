package com.github.liuzhuoming23.svea.app.service.impl;

import com.github.liuzhuoming23.svea.app.domain.Dict;
import com.github.liuzhuoming23.svea.app.domain.DictItem;
import com.github.liuzhuoming23.svea.app.mapper.DictItemMapper;
import com.github.liuzhuoming23.svea.app.mapper.DictMapper;
import com.github.liuzhuoming23.svea.app.service.DictItemService;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典项service
 *
 * @author liuzhuoming
 */
@Service
public class DictItemServiceImpl implements DictItemService {

    @Autowired
    private DictItemMapper dictItemMapper;
    @Autowired
    private DictMapper dictMapper;


    @Override
    public DictItem selectByDictIdAndDictItemVal(Integer dictId, Integer val) {
        DictItem dictItem = new DictItem();
        dictItem.setDictId(dictId);
        dictItem.setVal(val);
        DictItem one = dictItemMapper.selectByDictIdAndDictItemVal(dictItem);
        if (one == null) {
            throw new SveaException("dictItem not exist");
        }
        return one;
    }

    @Override
    public List<DictItem> selectByDictId(Integer dictId) {
        return dictItemMapper.selectByDictId(dictId);
    }

    @Override
    public DictItem insert(DictItem dictItem) {
        Dict dict = dictMapper.selectOneById(dictItem.getDictId());
        if (dict != null) {
            try {
                dictItemMapper.insert(dictItem);
            } catch (Exception e) {
                throw new SveaException("dictItem is exist");
            }
            return dictItemMapper.selectByDictIdAndDictItemVal(dictItem);
        } else {
            throw new SveaException("dict not exist");
        }
    }
}
