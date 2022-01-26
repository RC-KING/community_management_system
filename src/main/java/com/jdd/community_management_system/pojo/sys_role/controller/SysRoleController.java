package com.jdd.community_management_system.pojo.sys_role.controller;


import com.jdd.community_management_system.pojo.sys_role.entity.SysRole;
import com.jdd.community_management_system.pojo.sys_role.service.impl.SysRoleServiceImpl;
import com.jdd.community_management_system.utils.result_data.ResultUtils;
import com.jdd.community_management_system.utils.result_data.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@RestController
@RequestMapping("/api/sys_role")
public class SysRoleController {
    @Autowired
    SysRoleServiceImpl sysRoleService;

    @PostMapping
    @ApiOperation("新增角色")
    public ResultVo addSysRole(@RequestBody SysRole role){
        if(sysRoleService.save(role)){
            return ResultUtils.success("新增角色成功!",role);
        }else {
            return ResultUtils.success("新增角色失败!",role);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除角色")
    public ResultVo delSysRole(@PathVariable Long id){
        if(sysRoleService.removeById(id)){
            return ResultUtils.success("删除角色成功!",id);
        }else {
            return ResultUtils.success("删除角色失败!",id);
        }
    }

    @PatchMapping
    @ApiOperation("根据ID,修改角色")
    public ResultVo updateSysRole(@RequestBody SysRole role){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = sysRoleService.getById(role.getId()).getVersion();
        // 设置Version字段
        role.setVersion(version);
        // 采取更新措施
        if(sysRoleService.updateById(role)){
            return ResultUtils.success("更新角色成功!",role);
        }else {
            return ResultUtils.success("更新角色失败!",role);
        }
    }

    @GetMapping
    @ApiOperation("查询所有角色")
    public ResultVo getAllSysRole(){
        List<SysRole> list = sysRoleService.list();
        if(list.size()!=0){
            return ResultUtils.success("获取所有角色成功!",list);
        }else {
            return ResultUtils.success("获取所有角色失败!",list);
        }
    }
}

