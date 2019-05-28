package com.github.liuzhuoming23.svea.app.mapper;

import com.github.liuzhuoming23.svea.app.domain.Dict;
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
    Dict selectOneById(Integer id);

    /**
     * 添加字典
     *
     * @param dict 字典数据
     */
    void insert(Dict dict);

    /**
     * 删除字典
     *
     * @param id 字典id
     */
    void delete(Integer id);
}
