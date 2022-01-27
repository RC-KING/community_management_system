package com.jdd.community_management_system.pojo.sys_role_permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.community_management_system.pojo.sys_role_permission.entity.SysRolePermission;
import com.jdd.community_management_system.utils.dataUtils.RoleParam;
import com.jdd.community_management_system.utils.dataUtils.RolePermissionVo;

import java.util.List;

/**
 * <p>
 * 角色/权限表 服务类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {
     RolePermissionVo getAssignPermissionTree(RoleParam roleParam);

     void saveAssignRole(Long roleId, List<Long> list);
}
