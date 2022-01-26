package com.jdd.community_management_system.pojo.sys_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.mapper.SysUserMapper;
import com.jdd.community_management_system.pojo.sys_user.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
