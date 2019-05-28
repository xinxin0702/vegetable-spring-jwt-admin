package com.github.liuzhuoming23.svea.app.service;

import com.github.liuzhuoming23.svea.app.domain.Menu;

/**
 * 菜单service
 *
 * @author liuzhuoming
 */
public interface MenuService {

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
    Menu selectOneById(Integer id);
}
