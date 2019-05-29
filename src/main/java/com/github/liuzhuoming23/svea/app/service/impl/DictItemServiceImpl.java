package com.github.liuzhuoming23.svea.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public DictItem selectOneByDictIdAndDictItemVal(Integer dictId, Integer val) {
        LambdaQueryWrapper<DictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictItem::getDictId, dictId);
        wrapper.eq(DictItem::getVal, val);
        DictItem dictItem = dictItemMapper.selectOne(wrapper);
        if (dictItem == null) {
            throw new SveaException(
                String.format("dictItem(dictId=%d val=%d) not exist", dictId, val));
        }
        return dictItem;
    }

    @Override
    public List<DictItem> selectListByDictId(Integer dictId) {
        LambdaQueryWrapper<DictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictItem::getDictId, dictId);
        return dictItemMapper.selectList(wrapper);
    }

    @Override
    public void insert(DictItem dictItem) {
        Dict dict = dictMapper.selectById(dictItem.getDictId());
        if (dict != null) {
            try {
                dictItemMapper.insert(dictItem);
            } catch (Exception e) {
                throw new SveaException(String
                    .format("dictItem(dictId=%d val=%d) is already exist", dictItem.getDictId(),
                        dictItem.getVal()));
            }
        } else {
            throw new SveaException(String.format("dict(id=%d) not exist", dictItem.getDictId()));
        }
    }

    @Override
    public void deleteByDictIdAndDictItemVal(Integer dictId, Integer val) {
        LambdaQueryWrapper<DictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictItem::getDictId, dictId);
        wrapper.eq(DictItem::getVal, val);
        dictItemMapper.delete(wrapper);
    }
}
