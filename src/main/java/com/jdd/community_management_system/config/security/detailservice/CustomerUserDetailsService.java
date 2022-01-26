package com.jdd.community_management_system.config.security.detailservice;

import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    SysUserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUsername(username);
        if (null==user){
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        return user;
    }
}
