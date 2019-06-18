package com.github.liuzhuoming23.vegetable.admin.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.liuzhuoming23.vegetable.admin.app.domain.Role;
import com.github.liuzhuoming23.vegetable.admin.app.mapper.RoleMapper;
import com.github.liuzhuoming23.vegetable.admin.app.service.RoleService;
import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色service
 *
 * @author liuzhuoming
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void insert(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        if (role == null || role.getId() == null) {
            throw new VsjaException("role(id=null) not exist");
        }
        roleMapper.updateById(role);
    }

    @Override
    public List<Role> select(Role role) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (role != null) {
            wrapper.eq(role.getId() != null, Role::getId, role.getId());
            wrapper.eq(role.getEnable() != null, Role::getEnable, role.getEnable());
            wrapper.eq(StringUtils.isNotEmpty(role.getName()), Role::getName, role.getName());
        }
        return roleMapper.selectList(wrapper);
    }

    @Override
    public Role selectOneById(Integer id) {
        if (id == null) {
            throw new VsjaException("role(id=null) not exist");
        }
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new VsjaException(String.format("role(id=%d) not exist", id));
        }
        return role;
    }
}
