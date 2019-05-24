package com.github.liuzhuoming23.jwtback.app.service;

import com.github.liuzhuoming23.jwtback.app.domain.Dict;
import com.github.liuzhuoming23.jwtback.app.domain.DictItem;
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
     * 根据字典内容编码获取字典
     *
     * @param code 字典内容编码
     */
    Dict selectOneByCode(String code);

    /**
     * 添加字典
     *
     * @param dict 字典数据
     */
    Dict insert(Dict dict);

    /**
     * 根据字典id和字典内容值获取字典内容
     *
     * @param dictId 字典id
     * @param val 字典内容值
     */
    DictItem selectByDictIdAndVal(Integer dictId, Integer val);

    /**
     * 根据字典id获取字典内容集合
     *
     * @param dictId 字典id
     */
    List<DictItem> selectByDictId(Integer dictId);

    /**
     * 添加字典内容
     */
    DictItem insertItem(DictItem dictItem);
}
