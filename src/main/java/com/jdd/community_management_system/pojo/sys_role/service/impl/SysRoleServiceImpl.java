package com.jdd.community_management_system.pojo.sys_role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.pojo.sys_role.entity.SysRole;
import com.jdd.community_management_system.pojo.sys_role.mapper.SysRoleMapper;
import com.jdd.community_management_system.pojo.sys_role.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
