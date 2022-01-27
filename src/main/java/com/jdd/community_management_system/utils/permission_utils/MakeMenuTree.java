package com.jdd.community_management_system.utils.permission_utils;

import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MakeMenuTree {
    //                                                                                 父ID
    public static List<SysPermission> makeTree(List<SysPermission> permissionList, Long pid){
        // 创建一个新ArrayList
        List<SysPermission> list = new ArrayList<>();
                // 非空判断,若空则用一个新ArrayList替代
        Optional.ofNullable(permissionList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId() == pid)
                .forEach(item -> {
                    SysPermission parentPermission = new SysPermission();
                    // 将item的数据复制一份到 parentPermission 中
                    BeanUtils.copyProperties(item, parentPermission);
                    // 开始递归,将自己的ID传进去作为父ID
                    List<SysPermission> children = makeTree(permissionList,item.getId());
                    parentPermission.setChildren(children);
                    list.add(parentPermission);
                });
        return list;
    }
}
