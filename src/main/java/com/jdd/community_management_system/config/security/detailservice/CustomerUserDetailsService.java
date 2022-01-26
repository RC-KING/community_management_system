package com.jdd.community_management_system.config.security.detailservice;

import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.service.impl.SysPermissionServiceImpl;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    SysUserServiceImpl userService;
    @Autowired
    SysPermissionServiceImpl sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUsername(username);
        if (null==user){
            throw new UsernameNotFoundException("用户名或密码错误!");
        }

        //查询用户所有权限
        List<SysPermission> permissionList = sysPermissionService.getPermissionListByUserId(user.getId());
        List<String> collect =
                permissionList.stream()
                        .filter(Objects::nonNull)
                        .map(SysPermission::getCode)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        // 转换为数组
        String[] strings = collect.toArray(new String[collect.size()]);
        System.out.println(Arrays.toString(strings));
        // 将存放权限的字符串数组转化为权限数组
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        // 设置权限
        user.setAuthorities(authorityList);
        // 设置菜单列表
        user.setPermissionList(permissionList);
        return user;
    }
}
