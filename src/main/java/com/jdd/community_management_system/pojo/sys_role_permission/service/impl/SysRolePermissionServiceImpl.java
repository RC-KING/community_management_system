package com.jdd.community_management_system.pojo.sys_role_permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.mapper.SysPermissionMapper;
import com.jdd.community_management_system.pojo.sys_role_permission.entity.SysRolePermission;
import com.jdd.community_management_system.pojo.sys_role_permission.mapper.SysRolePermissionMapper;
import com.jdd.community_management_system.pojo.sys_role_permission.service.SysRolePermissionService;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.mapper.SysUserMapper;
import com.jdd.community_management_system.utils.dataUtils.RoleParam;
import com.jdd.community_management_system.utils.dataUtils.RolePermissionVo;
import com.jdd.community_management_system.utils.permission_utils.MakeMenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 角色/权限表 服务实现类
 *
 * @author 金大大
 * @since 2022-01-27
 */
@Service
public class SysRolePermissionServiceImpl
    extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
    implements SysRolePermissionService {

  @Autowired SysUserMapper sysUserMapper;
  @Autowired SysPermissionMapper sysPermissionMapper;

  @Override
  public RolePermissionVo getAssignPermissionTree(RoleParam roleParam) {
    // 1.查询当前用户信息
    SysUser user = sysUserMapper.selectById(roleParam.getUserId());
    // 2.如果用户是管理员，可以拥有所有的权限，如果不是，只能查询自己所拥有的权限
    List<SysPermission> permissionList = null;
    if (user.isAdmin()) {
      // 管理员可以获取所有的权限
      QueryWrapper<SysPermission> query = new QueryWrapper<>();
      permissionList = sysPermissionMapper.selectList(query);
    } else {
      // 不是管理员,根据自己的 userId,查询出对应拥有的权限 (通过用户的ID获取该用户拥有的所有权限)
      permissionList = sysPermissionMapper.getPermissionListByUserId(roleParam.getUserId());
    }
    // 返回值(菜单树数据 + 选中的菜单)
    RolePermissionVo vo = new RolePermissionVo();
    // 3.组装成树数据
    List<SysPermission> menuList = MakeMenuTree.makeTree(permissionList, 0L);
    // 管理员给某个角色分配权限,通过该角色ID,获取这个角色原本拥有的权限(做该角色的权限回显)
    List<SysPermission> rolePermission =
        sysPermissionMapper.getPermissionListByRoleId(roleParam.getRoleId());
    // 4.找出选中的数据
    List<Long> rolePermissionIdList = new ArrayList<>();
    // 4.1对权限列表进行查询
    Optional.ofNullable(permissionList).orElse(new ArrayList<>()).stream()
        .filter(Objects::nonNull)
        .forEach(item -> {
              // 4.2对 角色-权限对应表 进行循环查找
              Optional.ofNullable(rolePermission).orElse(new ArrayList<>()).stream()
                  .filter(Objects::nonNull)
                  .forEach(dom -> {
                        if (item.getId().equals(dom.getId())) {
                          // 如果查找到了就将对应的 id 存入 rolePermissionIdList
                          // 这个id是 权限id,用于权限树的checkbox的选择
                          rolePermissionIdList.add(dom.getId());
                        }
                      });
            });
    vo.setListmenu(menuList);
    vo.setCheckList(rolePermissionIdList.toArray());
    return vo;
  }

  @Transactional
  public void saveAssignRole(Long roleId, List<Long> perIdList) {
    // 1.删除该角色原来的全部权限
    QueryWrapper<SysRolePermission> query = new QueryWrapper<>();
    query.lambda().eq(SysRolePermission::getRoleId, roleId);
    this.baseMapper.delete(query);
    // 2.保存新的权限
    this.baseMapper.saveRolePermissions(roleId, perIdList);
  }
}
