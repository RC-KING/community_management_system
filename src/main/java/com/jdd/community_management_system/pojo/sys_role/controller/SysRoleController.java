package com.jdd.community_management_system.pojo.sys_role.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jdd.community_management_system.pojo.sys_role.entity.SysRole;
import com.jdd.community_management_system.pojo.sys_role.service.impl.SysRoleServiceImpl;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user_role.service.SysUserRoleService;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import com.jdd.community_management_system.utils.dataUtils.RolePageParam;
import com.jdd.community_management_system.utils.log.annotation.SysLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    SysUserRoleService sysUserRoleService;


    @SysLog("新增角色")
    @PostMapping
    @ApiOperation("新增角色")
    //@PreAuthorize("hasAuthority('sys:role:add')")
    public ResultVo addSysRole(@RequestBody SysRole role){
        SysUser currentOperator = (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        role.setCreateUserId(currentOperator.getId());
        if(sysRoleService.save(role)){
            return ResultUtils.success("新增角色成功!",role);
        }else {
            return ResultUtils.error("新增角色失败!",role);
        }
    }

    @SysLog("根据ID,删除单个删除角色")
    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除角色")
    //@PreAuthorize("hasAuthority('sys:role:delete')")
    public ResultVo delSysRole(@PathVariable Long id){
        if(sysRoleService.removeById(id)){
            return ResultUtils.success("删除角色成功!",id);
        }else {
            return ResultUtils.error("删除角色失败!",id);
        }
    }

    @SysLog("根据ID,修改角色")
    @PatchMapping
    @ApiOperation("根据ID,修改角色")
    // @PreAuthorize("hasAuthority('sys:role:edit')")
    public ResultVo updateSysRole(@RequestBody SysRole role){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = sysRoleService.getById(role.getId()).getVersion();
        // 设置Version字段
        role.setVersion(version);
        // 采取更新措施
        if(sysRoleService.updateById(role)){
            return ResultUtils.success("更新角色成功!",role);
        }else {
            return ResultUtils.error("更新角色失败!",role);
        }
    }

    @ApiOperation(value="查询角色列表")
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="角色名称"),
            @ApiImplicitParam(name="userId",value="当前登录用户id",required = true),
            @ApiImplicitParam(name="currentPage",value="当前页",required = true),
            @ApiImplicitParam(name="pageSize",value="页容量",required = true)
    })
    public ResultVo list(@RequestBody RolePageParam rolePageParam){
        IPage<SysRole> roleList = sysUserRoleService.roleList(rolePageParam);
        return ResultUtils.success("查询成功!",roleList);
    }
}

