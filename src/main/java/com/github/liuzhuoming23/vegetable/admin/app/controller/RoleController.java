package com.github.liuzhuoming23.vegetable.admin.app.controller;

import com.github.liuzhuoming23.vegetable.admin.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

}
