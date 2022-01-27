package com.jdd.community_management_system.utils.dataUtils;

import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RolePermissionVo {
    // 菜单数据
    List<SysPermission> listmenu = new ArrayList<>();
    // 原来分配的菜单
    private Object[] checkList;
}
