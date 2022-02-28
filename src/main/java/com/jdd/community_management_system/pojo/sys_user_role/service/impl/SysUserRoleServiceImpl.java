package com.jdd.community_management_system.pojo.sys_user_role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.config.security.exception.CustomerAuthenticationException;
import com.jdd.community_management_system.pojo.sys_role.entity.SysRole;
import com.jdd.community_management_system.pojo.sys_role.mapper.SysRoleMapper;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.mapper.SysUserMapper;
import com.jdd.community_management_system.pojo.sys_user_role.entity.SysUserRole;
import com.jdd.community_management_system.pojo.sys_user_role.mapper.SysUserRoleMapper;
import com.jdd.community_management_system.pojo.sys_user_role.service.SysUserRoleService;
import com.jdd.community_management_system.utils.dataUtils.RolePageParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户/角色表 服务实现类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public SysUserRole getRoleIdByUserId(Long userId) {
        QueryWrapper<SysUserRole> query = new QueryWrapper<>();
        query.lambda().eq(SysUserRole::getUserId, userId);
        return this.baseMapper.selectOne(query);
    }

    @Override
    public void assignRole(SysUserRole userRole) {
        QueryWrapper<SysUserRole> query = new QueryWrapper<>();
        // 通过userID查询出来多条记录
        query.lambda().eq(SysUserRole::getUserId, userRole.getUserId());
        // 先删除该用户原来的角色
        this.baseMapper.delete(query);
        // 然后更新数据(这里默认用户和角色是一对一关系,所以直接更新一条记录)
        this.baseMapper.insert(userRole);

    }

    @Autowired private SysUserMapper sysUserMapper;
    @Autowired private SysRoleMapper sysRoleMapper;

    @Override
    // 传入的是分页参数(当前页,页容量),当前用户ID,角色名称(筛选)
    public IPage<SysRole> roleList(RolePageParam rolePageParam) {
        // 查询当前用户
        SysUser user = sysUserMapper.selectById(rolePageParam.getUserId());
        if (user==null) {
            throw new CustomerAuthenticationException("无法获取用户信息!");
        }

        QueryWrapper<SysRole> query = new QueryWrapper<>();
        // 根据角色名称(name)筛选查询
        if (StringUtils.isNotEmpty(rolePageParam.getName())) {
            query.lambda().like(SysRole::getName, rolePageParam.getName());
        }
        // 如果是超级管理员，可以获取全部角色列表，否则只能获取自己建的角色列表
        if (!user.isAdmin()) {
            // 这个里面走的是 不是管理员 (is_admin = 0),只能获取自己创建的角色列表
            // 相当于如果走了这个分支,就会多一条限制,查询出来的记录就会少一些

            query.lambda().eq(SysRole::getCreateUserId, rolePageParam.getUserId());
        }
        IPage<SysRole> page = new Page<>();
        page.setCurrent(rolePageParam.getCurrentPage());
        page.setSize(rolePageParam.getPageSize());
        return sysRoleMapper.selectPage(page, query) ;
    }


}
