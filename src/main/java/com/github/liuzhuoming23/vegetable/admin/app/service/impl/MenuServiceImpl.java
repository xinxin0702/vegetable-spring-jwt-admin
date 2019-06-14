package com.github.liuzhuoming23.vegetable.admin.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Menu;
import com.github.liuzhuoming23.vegetable.admin.app.mapper.MenuMapper;
import com.github.liuzhuoming23.vegetable.admin.app.service.MenuService;
import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单service
 *
 * @author liuzhuoming
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void insert(Menu menu) {
        if (menu.getParentId() != null) {
            Menu parent = menuMapper.selectById(menu.getParentId());
            if (parent == null) {
                throw new VsjaException(String.format("menu(id=%d) not exist", menu.getParentId()));
            }
        }
        menuMapper.insert(menu);
    }

    @Override
    public void update(Menu menu) {
        if (menu == null || menu.getId() == null) {
            throw new VsjaException("menu(id=null) not exist");
        }
        Menu one = menuMapper.selectById(menu.getId());
        if (one == null) {
            throw new VsjaException(String.format("menu(id=%d) not exist", menu.getId()));
        }
        menuMapper.updateById(menu);
    }

    @Override
    public List<Menu> select(Menu menu) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        if (menu != null) {
            if (menu.getId() != null) {
                wrapper.eq(Menu::getId, menu.getId());
            }
            if (menu.getParentId() != null) {
                wrapper.eq(Menu::getParentId, menu.getParentId());
            }
            if (menu.getSort() != null) {
                wrapper.eq(Menu::getSort, menu.getSort());
            }
            if (menu.getEnable() != null) {
                wrapper.eq(Menu::getEnable, menu.getEnable());
            }
        }
        List<Menu> list = menuMapper.selectList(wrapper);
        for (Menu menu1 : list) {
            menu1.setChildren(this.selectListByParentId(menu1.getId()));
        }
        return list;
    }

    @Override
    public Menu selectOneById(Integer id) {

        Menu menu = menuMapper.selectById(id);
        menu.setChildren(this.selectListByParentId(id));
        return menu;
    }

    @Override
    public void deleteOneById(Integer id) {
        menuMapper.deleteById(id);
    }

    /**
     * 递归获取所有子菜单
     *
     * @param parentId 父菜单id
     */
    private List<Menu> selectListByParentId(Integer parentId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, parentId);
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu menu : menus) {
            menu.setChildren(selectListByParentId(menu.getId()));
        }
        return menus;
    }
}
