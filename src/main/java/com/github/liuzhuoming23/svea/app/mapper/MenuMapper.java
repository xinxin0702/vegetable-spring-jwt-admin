package com.github.liuzhuoming23.svea.app.mapper;

import com.github.liuzhuoming23.svea.app.domain.Menu;
import java.util.List;

/**
 * 菜单mapper
 *
 * @author liuzhuoming
 */
public interface MenuMapper {

    /**
     * 新增菜单
     *
     * @param menu 菜单信息
     */
    void insert(Menu menu);

    /**
     * 根据id获取菜单信息
     *
     * @param id 菜单id
     */
    Menu selectOneById(int id);

    /**
     * 根据父菜单id获取菜单集合
     *
     * @param parentId 父菜单id
     */
    List<Menu> selectListByParentId(Integer parentId);
}
