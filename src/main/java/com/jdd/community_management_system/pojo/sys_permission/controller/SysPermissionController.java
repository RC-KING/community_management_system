package com.jdd.community_management_system.pojo.sys_permission.controller;


import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.service.impl.SysPermissionServiceImpl;
import com.jdd.community_management_system.utils.result_data.ResultUtils;
import com.jdd.community_management_system.utils.result_data.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    SysPermissionServiceImpl sysPermission;

    @PostMapping
    @ApiOperation("新增资源")
    public ResultVo addSysPermission(@RequestBody SysPermission permission){
        if(sysPermission.save(permission)){
            return ResultUtils.success("新增资源成功!",permission);
        }else {
            return ResultUtils.success("新增资源失败!",permission);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除资源")
    public ResultVo delSysPermission(@PathVariable Long id){
        if(sysPermission.removeById(id)){
            return ResultUtils.success("删除资源成功!",id);
        }else {
            return ResultUtils.success("删除资源失败!",id);
        }
    }

    @PatchMapping
    @ApiOperation("根据ID,修改资源")
    public ResultVo updateSysPermission(@RequestBody SysPermission permission){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = sysPermission.getById(permission.getId()).getVersion();
        // 设置Version字段
        permission.setVersion(version);
        // 采取更新措施
        if(sysPermission.updateById(permission)){
            return ResultUtils.success("更新资源成功!",permission);
        }else {
            return ResultUtils.success("更新资源失败!",permission);
        }
    }

    @GetMapping
    @ApiOperation("查询所有资源")
    public ResultVo getAllSysPermission(){
        List<SysPermission> list = sysPermission.list();
        if(list.size()!=0){
            return ResultUtils.success("获取所有资源成功!",list);
        }else {
            return ResultUtils.success("获取所有资源失败!",list);
        }
    }
}

