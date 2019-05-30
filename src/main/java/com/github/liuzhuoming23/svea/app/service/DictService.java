package com.github.liuzhuoming23.svea.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.liuzhuoming23.svea.app.domain.Dict;
import com.github.liuzhuoming23.svea.common.domain.PageParams;
import com.github.liuzhuoming23.svea.common.domain.SortParams;
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
     * @param dict 字典查询
     */
    List<Dict> select(Dict dict);

    /**
     * 获取分页字典列表
     *
     * @param dict 字典查询
     * @param pageParams 分页参数
     * @param sortParams 排序参数
     */
    IPage<Dict> page(Dict dict, PageParams pageParams, SortParams sortParams);

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
    void insert(Dict dict);

    /**
     * 删除字典
     *
     * @param code 字典编码
     */
    void delete(String code);
}
