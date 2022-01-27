package com.jdd.community_management_system.pojo.sys_user.controller;


import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.impl.SysUserServiceImpl;
import com.jdd.community_management_system.utils.redis.RedisService;
import com.jdd.community_management_system.utils.result_data.ResultUtils;
import com.jdd.community_management_system.utils.result_data.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@RestController
@RequestMapping("/api/sys_user")
public class SysUserController {
    @Autowired
    SysUserServiceImpl sysUserService;

    @PostMapping
    @ApiOperation("新增用户")
    public ResultVo addSysUser(@RequestBody SysUser user){
        if(sysUserService.save(user)){
            return ResultUtils.success("新增用户成功!",user);
        }else {
            return ResultUtils.error("新增用户失败!",user);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除用户")
    public ResultVo delSysUser(@PathVariable Long id){
        if(sysUserService.removeById(id)){
            return ResultUtils.success("删除用户成功!",id);
        }else {
            return ResultUtils.error("删除用户失败!",id);
        }
    }

    @PatchMapping()
    @ApiOperation("根据ID,修改用户")
    public ResultVo updateSysUser(@RequestBody SysUser user){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = sysUserService.getById(user.getId()).getVersion();
        // 设置Version字段
        user.setVersion(version);
        // 采取更新措施
        if(sysUserService.updateById(user)){
            return ResultUtils.success("更新用户成功!",user);
        }else {
            return ResultUtils.error("更新用户失败!",user);
        }
    }

    @GetMapping
    @ApiOperation("查询所有用户")
    public ResultVo getAllSysUser(){
        List<SysUser> list = sysUserService.list();
        if(list.size()!=0){
            return ResultUtils.success("获取所有用户成功!",list);
        }else {
            return ResultUtils.error("获取所有用户失败!",list);
        }
    }
//////////////////////////////////////////////////////////////////////////////////

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResultVo sysUserRegister(@RequestBody SysUser user){
        // 1.判断用户名是否重复
        SysUser user1 = sysUserService.getUserByUsername(user.getUsername());
        if(user1!=null){return ResultUtils.error("已存在该用户,请使用其他用户名注册!");}
        // 2.密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(sysUserService.save(user)){
            return ResultUtils.success("注册成功!",user);
        }else {
            return ResultUtils.error("注册失败!",user);
        }
    }


    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/test_redis")
    public ResultVo getAllUser(){
        redisService.set("name", "张三",60L);
        String name = redisService.get("name");
        List<SysUser> list = sysUserService.list();
        return new ResultVo("成功",200,name);
    }



}

