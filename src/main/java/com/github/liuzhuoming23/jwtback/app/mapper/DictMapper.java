package com.github.liuzhuoming23.jwtback.app.mapper;

import com.github.liuzhuoming23.jwtback.app.domain.Dict;
import com.github.liuzhuoming23.jwtback.app.domain.DictItem;
import java.util.List;

/**
 * 字典mapper
 *
 * @author liuzhuoming
 */
public interface DictMapper {

    /**
     * 获取字典列表
     *
     * @param dict 字典查询信息
     */
    List<Dict> select(Dict dict);

    /**
     * 根据字典编码获取字典
     *
     * @param code 字典编码
     */
    Dict selectOneByCode(String code);

    /**
     * 根据字典编码获取字典
     *
     * @param id 字典id
     */
    Dict selectOneById(int id);

    /**
     * 添加字典
     *
     * @param dict 字典数据
     */
    void insert(Dict dict);

    /**
     * 根据字典id获取字典项集合
     *
     * @param code 字典code
     */
    List<DictItem> selectByDictCode(String code);

    /**
     * 根据字典id和字典项值获取字典项
     *
     * @param dictItem 字典项查询信息
     */
    DictItem selectByDictIdAndVal(DictItem dictItem);

    /**
     * 根据字典id获取字典项集合
     *
     * @param dictId 字典id
     */
    List<DictItem> selectByDictId(Integer dictId);

    /**
     * 添加字典项
     */
    void insertItem(DictItem dictItem);
}
