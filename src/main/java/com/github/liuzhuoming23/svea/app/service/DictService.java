package com.github.liuzhuoming23.svea.app.service;

import com.github.liuzhuoming23.svea.app.domain.Dict;
import com.github.liuzhuoming23.svea.app.domain.DictItem;
import java.util.List;

/**
 * 字典service
 *
 * @author liuzhuoming
 */
public interface DictService {

    /**
     * 获取字典列表
     *
     * @param dict 字典查询信息
     */
    List<Dict> select(Dict dict);

    /**
     * 根据字典项编码获取字典
     *
     * @param code 字典项编码
     */
    Dict selectOneByCode(String code);

    /**
     * 添加字典
     *
     * @param dict 字典数据
     */
    Dict insert(Dict dict);

    /**
     * 根据字典id和字典项值获取字典项
     *
     * @param dictId 字典id
     * @param val 字典项值
     */
    DictItem selectByDictIdAndVal(Integer dictId, Integer val);

    /**
     * 根据字典id获取字典项集合
     *
     * @param dictId 字典id
     */
    List<DictItem> selectByDictId(Integer dictId);

    /**
     * 添加字典项
     */
    DictItem insertItem(DictItem dictItem);
}
