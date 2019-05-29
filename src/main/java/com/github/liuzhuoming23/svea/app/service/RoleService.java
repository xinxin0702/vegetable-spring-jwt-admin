package com.github.liuzhuoming23.svea.app.service;

import com.github.liuzhuoming23.svea.app.domain.Role;
import java.util.List;

/**
 * 角色service
 *
 * @author liuzhuoming
 */
public interface RoleService {

    /**
     * 添加角色
     *
     * @param role 角色
     */
    void insert(Role role);

    /**
     * 更新角色
     *
     * @param role 角色
     */
    void update(Role role);

    /**
     * 获取角色列表
     *
     * @param role 角色查询
     */
    List<Role> select(Role role);

    /**
     * 根据id获取角色
     *
     * @param id 角色id
     */
    Role selectOneById(Integer id);
}
