package com.jdd.community_management_system.pojo.sys_user_role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.community_management_system.pojo.sys_role.entity.SysRole;
import com.jdd.community_management_system.pojo.sys_user_role.entity.SysUserRole;
import com.jdd.community_management_system.utils.dataUtils.RolePageParam;

/**
 * <p>
 * 用户/角色表 服务类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    SysUserRole getRoleIdByUserId(Long userId);
    void assignRole(SysUserRole userRole);

    IPage<SysRole> roleList(RolePageParam param);
}
