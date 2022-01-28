package com.jdd.community_management_system.pojo.sys_permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.mapper.SysPermissionMapper;
import com.jdd.community_management_system.pojo.sys_permission.service.SysPermissionService;
import com.jdd.community_management_system.utils.permission_utils.MakeMenuTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 权限 服务实现类
 *
 * @author 金大大
 * @since 2022-01-25
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
    implements SysPermissionService {
  @Override
  public List<SysPermission> getPermissionListByUserId(Long userId) {
    return this.baseMapper.getPermissionListByUserId(userId);
  }

  @Override
  public List<SysPermission> getPermissionListByRoleId(Long roleId) {
    return this.baseMapper.getPermissionListByRoleId(roleId);
  }

  @Override
  public List<SysPermission> getPermissionTree() {
    QueryWrapper<SysPermission> query = new QueryWrapper<>();
    query.lambda().orderByAsc(SysPermission::getOrderNum);
    List<SysPermission> list = this.baseMapper.selectList(query);
    // 生成树数据
    return MakeMenuTree.makeTree(list, 0L);
  }

  @Override
  public List<SysPermission> getParentPermissionTree() {
    // 只查询目录和菜单
    String[] typeStr = {"0", "1"};
    List<String> allowTypes = Arrays.asList(typeStr);
    QueryWrapper<SysPermission> query = new QueryWrapper<>();
    query.lambda().in(SysPermission::getType, allowTypes).orderByAsc(SysPermission::getOrderNum);
    List<SysPermission> menuList = this.baseMapper.selectList(query);
    // 构造顶级菜单；如果数据库没有数据，选择上级菜单的时候需要展示 顶级菜单
    SysPermission menu = new SysPermission();
    menu.setId(0L);
    menu.setParentId(-1L);
    menu.setLabel("顶级菜单");
    menuList.add(menu);
    // 构造树形数据
    return MakeMenuTree.makeTree(menuList, -1L);
  }
}
