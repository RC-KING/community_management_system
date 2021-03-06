package com.jdd.community_management_system.pojo.sys_user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */

public interface SysUserService extends IService<SysUser> {
    SysUser getUserByUsername(String username);


    // 根据部门id查询用户列表
    IPage<SysUser> getUserList(Long currentPage, Long pageSize, Long deptId);
}
