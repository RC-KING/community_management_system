package com.jdd.community_management_system.pojo.sys_user_role.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jdd.community_management_system.pojo.sys_role.entity.SysRole;
import com.jdd.community_management_system.pojo.sys_user_role.entity.SysUserRole;
import com.jdd.community_management_system.pojo.sys_user_role.service.SysUserRoleService;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import com.jdd.community_management_system.utils.dataUtils.RolePageParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户/角色表 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
@RestController
@RequestMapping("/api/sys_user_role")
public class SysUserRoleController {
    @Autowired
    SysUserRoleService sysUserRoleService;


    @ApiOperation(value = "获取用户拥有的角色")
    @GetMapping("/getRoleIdByUserId/{userId}")
    public ResultVo getRoleIdByUserId(@PathVariable(value = "userId") Long userId) {
        SysUserRole userRole = sysUserRoleService.getRoleIdByUserId(userId);
        return ResultUtils.success("查询成功", userRole);
    }

    // @PreAuthorize("hasAuthority('sys:user:assign')")
    @ApiOperation(value = "分配角色--保存(设计为用户角色一对一)")
    @PostMapping("/assignRole")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true),
            @ApiImplicitParam(name = "userId", value = "被分配用户的id")
    })
    public ResultVo assignRole(@RequestBody SysUserRole userRole) {
        sysUserRoleService.assignRole(userRole);
        return ResultUtils.success("分配角色成功");
    }
    /**
     * 获取当前用户的角色列表
     */
    @ApiOperation(value = "分配角色--获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录用户id", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true)
    })
    @GetMapping("/getRoleListForAssign")
    public ResultVo getRoleListForAssign(@RequestBody RolePageParam param) {
        IPage<SysRole> roleList = sysUserRoleService.roleList(param);
        return ResultUtils.success("查询成功!", roleList);
    }

}

