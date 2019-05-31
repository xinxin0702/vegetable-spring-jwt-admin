package com.github.liuzhuoming23.vegetable.admin.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.liuzhuoming23.vegetable.admin.app.domain.DictItem;
import java.util.List;

/**
 * 字典项mapper
 *
 * @author liuzhuoming
 */
public interface DictItemMapper extends BaseMapper<DictItem> {

    /**
     * 根据字典id获取字典项集合
     *
     * @param code 字典code
     */
    List<DictItem> selectListByDictCode(String code);
}
