package com.jdd.community_management_system.pojo.sys_role_permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.community_management_system.pojo.sys_role_permission.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色/权限表 Mapper 接口
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    boolean saveRolePermissions(@Param("roleId") Long roleId,@Param("permissionIdList") List<Long> permissionIdList);
}
