package com.github.liuzhuoming23.vegetable.admin.app.service;

import com.github.liuzhuoming23.vegetable.admin.app.domain.DictItem;
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
    DictItem selectOneByDictIdAndDictItemVal(Integer dictId, Integer val);

    /**
     * 根据字典id获取字典项集合
     *
     * @param dictId 字典id
     */
    List<DictItem> selectListByDictId(Integer dictId);

    /**
     * 添加字典项
     */
    void insert(DictItem dictItem);

    /**
     * 根据字典id和字典项值删除字典项
     *
     * @param dictId 字典id
     * @param val 字典项值
     */
    void deleteByDictIdAndDictItemVal(Integer dictId, Integer val);
}
