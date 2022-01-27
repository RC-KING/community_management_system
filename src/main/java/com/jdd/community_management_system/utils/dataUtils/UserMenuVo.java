package com.jdd.community_management_system.utils.dataUtils;

import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import lombok.Data;

import java.util.List;
@Data
public class UserMenuVo {
    // 菜单数据
    private List<SysPermission> menuList;
    // 路由数据
    private List<SysPermission> routerList;
    // 按钮权限字段
    private List<String> authList;
}