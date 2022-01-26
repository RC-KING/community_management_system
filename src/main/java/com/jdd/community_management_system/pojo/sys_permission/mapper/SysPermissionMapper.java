package com.jdd.community_management_system.pojo.sys_permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    //根据用户id,查询某用户拥有的权限
    List<SysPermission> getPermissionListByUserId(@Param("userId") Long userId);

    //根据角色id,查询某角色拥有的权限
    List<SysPermission> getPermissionListByRoleId(@Param("roleId") Long roleId);

}
