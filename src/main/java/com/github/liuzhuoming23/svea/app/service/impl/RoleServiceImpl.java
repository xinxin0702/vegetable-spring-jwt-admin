package com.github.liuzhuoming23.svea.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.liuzhuoming23.svea.app.domain.Role;
import com.github.liuzhuoming23.svea.app.mapper.RoleMapper;
import com.github.liuzhuoming23.svea.app.service.RoleService;
import com.github.liuzhuoming23.svea.common.exception.SveaException;
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
            throw new SveaException("role(id=null) not exist");
        }
        roleMapper.updateById(role);
    }

    @Override
    public List<Role> select(Role role) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (role != null) {
            if (role.getId() != null) {
                wrapper.eq(Role::getId, role.getId());
            }
            if (role.getEnable() != null) {
                wrapper.eq(Role::getEnable, role.getEnable());
            }
            if (StringUtils.isNotEmpty(role.getName())) {
                wrapper.eq(Role::getName, role.getName());
            }
        }
        return roleMapper.selectList(wrapper);
    }

    @Override
    public Role selectOneById(Integer id) {
        if (id == null) {
            throw new SveaException("role(id=null) not exist");
        }
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new SveaException(String.format("role(id=%d) not exist", id));
        }
        return role;
    }
}
