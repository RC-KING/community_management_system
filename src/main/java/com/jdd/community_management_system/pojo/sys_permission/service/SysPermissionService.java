package com.jdd.community_management_system.pojo.sys_permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
public interface SysPermissionService extends IService<SysPermission> {
    List<SysPermission> getPermissionListByUserId(@Param("userId") Long userId);
    List<SysPermission> getPermissionListByRoleId(@Param("roleId") Long roleId);
    List<SysPermission> getPermissionTree();
    List<SysPermission> getParentPermissionTree();
}

