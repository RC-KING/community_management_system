package com.jdd.community_management_system.pojo.sys_permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.mapper.SysPermissionMapper;
import com.jdd.community_management_system.pojo.sys_permission.service.SysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Override
    public List<SysPermission> getPermissionListByUserId(Long userId) {
        return this.baseMapper.getPermissionListByUserId(userId);
    }

    @Override
    public List<SysPermission> getPermissionListByRoleId(Long roleId) {
        return this.baseMapper.getPermissionListByRoleId(roleId);
    }
}
