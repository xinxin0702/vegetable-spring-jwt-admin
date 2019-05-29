package com.github.liuzhuoming23.svea.app.service;

import com.github.liuzhuoming23.svea.app.domain.Menu;
import java.util.List;

/**
 * 菜单service
 *
 * @author liuzhuoming
 */
public interface MenuService {

    /**
     * 新增菜单
     *
     * @param menu 菜单
     */
    void insert(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu 菜单
     */
    void update(Menu menu);

    /**
     * 获取菜单列表
     *
     * @param menu 查询条件
     */
    List<Menu> select(Menu menu);

    /**
     * 根据id获取菜单
     *
     * @param id 菜单id
     */
    Menu selectOneById(Integer id);

    /**
     * 根据id删除菜单
     *
     * @param id 菜单id
     */
    void deleteOneById(Integer id);
}
