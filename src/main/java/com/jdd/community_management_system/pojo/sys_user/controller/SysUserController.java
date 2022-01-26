package com.jdd.community_management_system.pojo.sys_user.controller;


import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.impl.SysUserServiceImpl;
import com.jdd.community_management_system.utils.result_data.ResultUtils;
import com.jdd.community_management_system.utils.result_data.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ResultUtils.success("新增用户失败!",user);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除用户")
    public ResultVo delSysUser(@PathVariable Long id){
        if(sysUserService.removeById(id)){
            return ResultUtils.success("删除用户成功!",id);
        }else {
            return ResultUtils.success("删除用户失败!",id);
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
            return ResultUtils.success("更新用户失败!",user);
        }
    }

    @GetMapping
    @ApiOperation("查询所有用户")
    public ResultVo getAllSysUser(){
        List<SysUser> list = sysUserService.list();
        if(list.size()!=0){
            return ResultUtils.success("获取所有用户成功!",list);
        }else {
            return ResultUtils.success("获取所有用户失败!",list);
        }
    }
}

