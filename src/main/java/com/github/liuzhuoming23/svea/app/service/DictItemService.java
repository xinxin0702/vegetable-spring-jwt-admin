package com.github.liuzhuoming23.svea.app.service;

import com.github.liuzhuoming23.svea.app.domain.DictItem;
import java.util.List;

/**
 * 字典项service
 *
 * @author liuzhuoming
 */
public interface DictItemService {

    /**
     * 根据字典id和字典项值获取字典项
     *
     * @param dictId 字典id
     * @param val 字典项值
     */
    DictItem selectByDictIdAndDictItemVal(Integer dictId, Integer val);

    /**
     * 根据字典id获取字典项集合
     *
     * @param dictId 字典id
     */
    List<DictItem> selectByDictId(Integer dictId);

    /**
     * 添加字典项
     */
    void insert(DictItem dictItem);
}
