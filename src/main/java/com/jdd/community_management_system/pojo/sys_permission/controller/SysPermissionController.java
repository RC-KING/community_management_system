package com.jdd.community_management_system.pojo.sys_permission.controller;


import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.service.impl.SysPermissionServiceImpl;
import com.jdd.community_management_system.utils.result_data.ResultUtils;
import com.jdd.community_management_system.utils.result_data.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资源 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@RestController
@RequestMapping("/api/sys_permission")
public class SysPermissionController {
    @Autowired
    SysPermissionServiceImpl sysPermissionService;

    @PostMapping
    @ApiOperation("新增资源")
    public ResultVo addSysPermission(@RequestBody SysPermission permission){
        if(sysPermissionService.save(permission)){
            return ResultUtils.success("新增资源成功!",permission);
        }else {
            return ResultUtils.error("新增资源失败!",permission);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除资源")
    public ResultVo delSysPermission(@PathVariable Long id){
        if(sysPermissionService.removeById(id)){
            return ResultUtils.success("删除资源成功!",id);
        }else {
            return ResultUtils.error("删除资源失败!",id);
        }
    }

    @PatchMapping
    @ApiOperation("根据ID,修改资源")
    public ResultVo updateSysPermission(@RequestBody SysPermission permission){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = sysPermissionService.getById(permission.getId()).getVersion();
        // 设置Version字段
        permission.setVersion(version);
        // 采取更新措施
        if(sysPermissionService.updateById(permission)){
            return ResultUtils.success("更新资源成功!",permission);
        }else {
            return ResultUtils.error("更新资源失败!",permission);
        }
    }

    @GetMapping
    @ApiOperation("查询所有资源")
    public ResultVo getAllSysPermission(){
        List<SysPermission> list = sysPermissionService.list();
        if(list.size()!=0){
            return ResultUtils.success("获取所有资源成功!",list);
        }else {
            return ResultUtils.error("获取所有资源失败!",list);
        }
    }

    //////////////////////////////////////////////////////////////////////////

    @GetMapping("/sys_user/{userId}")
    @ApiOperation("根据用户ID查询,该用户拥有的资源")
    public ResultVo getSysPermissionListBySysUserId(@PathVariable Long userId){
        List<SysPermission> list=new ArrayList<>();
        try{
            list = sysPermissionService.getPermissionListByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.error("获取用户权限失败!",list);
        }
       if(list.size()==0){
           return ResultUtils.success("用户没有任何权限!",list);
        }else {
           return ResultUtils.success("获取用户权限成功!",list);

        }
    }

    @GetMapping("/sys_role/{roleId}")
    @ApiOperation("根据角色ID查询,该角色拥有的资源")
    public ResultVo getSysPermissionListBySysRoleId(@PathVariable Long roleId){
        List<SysPermission> list=new ArrayList<>();
        try{
            list = sysPermissionService.getPermissionListByRoleId(roleId);
        }catch (Exception e){
            return ResultUtils.error("获取角色权限失败!",list);
        }
        if(list.size()==0){
            return ResultUtils.success("角色没有任何权限!",list);
        }else {
            return ResultUtils.success("获取角色权限成功!",list);

        }
    }



}

