package com.github.liuzhuoming23.vegetable.admin.app.controller;

import com.github.liuzhuoming23.vegetable.admin.app.domain.Menu;
import com.github.liuzhuoming23.vegetable.admin.app.service.MenuService;
import com.github.liuzhuoming23.vegetable.admin.common.annotation.Log;
import com.github.liuzhuoming23.vegetable.admin.common.cons.LogLevel;
import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    @Log(description = "获取菜单列表", level = LogLevel.LV1)
    public Result select(Menu menu) {
        List<Menu> list = menuService.select(menu);
        return new Result().succ(list);
    }

    @GetMapping("{id}")
    @Log(description = "获取菜单", level = LogLevel.LV1)
    public Result selectOneById(@PathVariable Integer id) {
        Menu menu = menuService.selectOneById(id);
        return new Result().succ(menu);
    }

    @PostMapping
    @Log(description = "添加菜单", level = LogLevel.LV2)
    public void insert(@Valid Menu menu) {
        menuService.insert(menu);
    }

    @DeleteMapping("{id}")
    @Log(description = "删除菜单", level = LogLevel.LV2)
    public void delete(@PathVariable Integer id) {
        menuService.deleteOneById(id);
    }

    @PutMapping("{id}")
    @Log(description = "更新菜单", level = LogLevel.LV2)
    public void update(@Valid Menu menu, @PathVariable Integer id) {
        menu.setId(id);
        menuService.update(menu);
    }
}
