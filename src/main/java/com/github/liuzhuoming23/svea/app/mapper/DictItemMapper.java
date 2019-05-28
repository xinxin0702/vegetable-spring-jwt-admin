package com.github.liuzhuoming23.svea.app.mapper;

import com.github.liuzhuoming23.svea.app.domain.DictItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 字典项mapper
 *
 * @author liuzhuoming
 */
public interface DictItemMapper {

    /**
     * 根据字典id获取字典项集合
     *
     * @param code 字典code
     */
    List<DictItem> selectByDictCode(String code);

    /**
     * 根据字典id和字典项值获取字典项
     *
     * @param dictId 字典id
     * @param val 字典项值
     */
    DictItem selectByDictIdAndDictItemVal(@Param("dictId") Integer dictId,
        @Param("val") Integer val);

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

    /**
     * 根据字典id删除字典项集合
     *
     * @param dictId 字典id
     */
    void deleteByDictId(Integer dictId);

    /**
     * 根据字典id和字典项值删除字典项
     *
     * @param dictId 字典id
     * @param val 字典项值
     */
    void deleteByDictIdAndDictItemVal(@Param("dictId") Integer dictId, @Param("val") Integer val);
}
