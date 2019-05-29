package com.github.liuzhuoming23.svea.app.service.impl;

import com.github.liuzhuoming23.svea.app.domain.Menu;
import com.github.liuzhuoming23.svea.app.mapper.MenuMapper;
import com.github.liuzhuoming23.svea.app.service.MenuService;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
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
            Menu parent = menuMapper.selectOneById(menu.getParentId());
            if (parent == null) {
                throw new SveaException("menu(id=" + menu.getParentId() + ") not exist");
            }
        }
        menuMapper.insert(menu);
    }

    @Override
    public void update(Menu menu) {
        Menu one = menuMapper.selectOneById(menu.getId());
        if (menu.getId() == null || one == null) {
            throw new SveaException("menu(id=" + menu.getId() + ") not exist");
        }
        menuMapper.update(menu);
    }

    @Override
    public Menu selectOneById(Integer id) {
        Menu menu = menuMapper.selectOneById(id);
        menu.setChildren(this.selectListByParentId(id));
        return menu;
    }

    @Override
    public void deleteOneById(Integer id) {
        menuMapper.deleteOneById(id);
    }

    /**
     * 递归获取所有子菜单
     *
     * @param parentId 父菜单id
     */
    private List<Menu> selectListByParentId(Integer parentId) {
        List<Menu> menus = menuMapper.selectListByParentId(parentId);
        for (Menu menu : menus) {
            menu.setChildren(selectListByParentId(menu.getId()));
        }
        return menus;
    }
}
