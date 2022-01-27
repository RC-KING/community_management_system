package com.jdd.community_management_system.pojo.sys_role_permission.controller;


import com.jdd.community_management_system.pojo.sys_role_permission.service.impl.SysRolePermissionServiceImpl;
import com.jdd.community_management_system.utils.dataUtils.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色/权限表 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
@RestController
@RequestMapping("/api/sys_role_permission")
public class SysRolePermissionController {

    @Autowired
    SysRolePermissionServiceImpl sysRolePermissionService;

    @ApiOperation(value = "给角色分配权限之前的获取树形权限数据")
    @GetMapping("/getAssignPermissionTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "roleId", value = "要分配权限的角色的id", required = true)
    })
    public ResultVo getAssignPermissionTree(@RequestBody RoleParam roleParam) {
        RolePermissionVo assignPermissionTree = sysRolePermissionService.getAssignPermissionTree(roleParam);
        return ResultUtils.success("查询成功", assignPermissionTree);
    }



    @ApiOperation(value = "给角色分配权限--保存分配权限操作")
    @PostMapping("/roleAssignSave")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true),
            @ApiImplicitParam(name = "permissionIdList", value = "存放权限id的一个数组")
    })
    //@PreAuthorize("hasAuthority('sys:role:assign')")
    public ResultVo roleAssignSave(@RequestBody RolePermissionParam permissionParam) {
        sysRolePermissionService.saveAssignRole(permissionParam.getRoleId(), permissionParam.getPermissionIdList());
        return ResultUtils.success("分配成功!");
    }

}

