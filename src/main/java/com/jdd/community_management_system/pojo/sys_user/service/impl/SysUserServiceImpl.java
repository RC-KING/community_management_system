package com.jdd.community_management_system.pojo.sys_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public SysUser getUserByUsername(String username) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("username", username);
        return this.baseMapper.selectOne(sysUserQueryWrapper);
    }

    @Override
    public IPage<SysUser> getUserList(Long currentPage, Long pageSize, Long deptId) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        // TODO: 这个地方应当根据likeId查询,不然非叶子部门的人数就显示不出来
        query.lambda().eq(SysUser::getDeptId,deptId);
        IPage<SysUser> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);
        return this.baseMapper.selectPage(page,query);
    }


}
