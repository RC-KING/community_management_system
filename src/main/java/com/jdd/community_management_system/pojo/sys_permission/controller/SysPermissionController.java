package com.jdd.community_management_system.pojo.sys_permission.controller;


import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import com.jdd.community_management_system.pojo.sys_permission.service.impl.SysPermissionServiceImpl;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import com.jdd.community_management_system.utils.dataUtils.UserMenuVo;
import com.jdd.community_management_system.utils.log.annotation.SysLog;
import com.jdd.community_management_system.utils.permission_utils.MakeMenuTree;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @SysLog("新增资源")
    @PostMapping
    @ApiOperation("新增资源")
    public ResultVo addSysPermission(@RequestBody SysPermission permission){
        if(sysPermissionService.save(permission)){
            return ResultUtils.success("新增资源成功!",permission);
        }else {
            return ResultUtils.error("新增资源失败!",permission);
        }
    }

    @SysLog("根据ID,删除单个删除资源")
    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除资源")
    public ResultVo delSysPermission(@PathVariable Long id){
        if(sysPermissionService.removeById(id)){
            return ResultUtils.success("删除资源成功!",id);
        }else {
            return ResultUtils.error("删除资源失败!",id);
        }
    }

    @SysLog("根据ID,修改资源")
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

    @SysLog("查询所有资源-获取的是一维数据")
    @GetMapping
    @ApiOperation("查询所有资源-获取的是一维数据")
    public ResultVo getAllSysPermission(){
        List<SysPermission> list = sysPermissionService.list();
        if(list.size()!=0){
            return ResultUtils.success("获取所有资源成功!",list);
        }else {
            return ResultUtils.error("获取所有资源失败!",list);
        }
    }

    //////////////////////////////////////////////////////////////////////////

    @SysLog(value = "获取当前用户的 菜单数据+权限数据+路由数据 获取的是树形数据 ")
    @ApiOperation(value = "获取当前用户的 菜单数据+权限数据+路由数据 获取的是树形数据 ")
    @GetMapping("/getOperatorPermissionInfo")
    public ResultVo getPermissionList() {
        // 用户相关的信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户信息
        SysUser user = (SysUser) authentication.getPrincipal();
        // 获取用户所有的权限信息(完整菜单数据)
        List<SysPermission> permissionList = user.getPermissionList();
        UserMenuVo vo = new UserMenuVo();
        // 将 permissionList 中的 权限字段 提取出来
        List<String> codeList = permissionList.stream()
                .filter(Objects::nonNull)
                .map(SysPermission::getCode)
                .collect(Collectors.toList());
        vo.setAuthList(codeList);

        // 获取菜单数据 type="2" 的都是按钮级别的,  type="0"和 type="1"的分别是目录和菜单
        // 首先过滤一道,将空数据和按钮级别的权限给排除掉
        List<SysPermission> menuList = permissionList.stream()
                .filter(item -> item != null && !item.getType().equals("2"))
                .collect(Collectors.toList());
        // 将剩下的 一维数据转 换成 树 的格式
        List<SysPermission> menus = MakeMenuTree.makeTree(menuList, 0L);
        vo.setMenuList(menus);

        // 获路由数据, 只有type="1"的时候,存放的才是页面链接,才有对应的组件路由地址,需要路由地址
        List<SysPermission> routerList = permissionList.stream()
                        .filter(item -> item != null && item.getType().equals("1"))
                        .collect(Collectors.toList());
        vo.setRouterList(routerList);
        return ResultUtils.success("成功", vo);
    }


    @SysLog("查询所有资源-获取的是树形数据")
    @GetMapping("/getPermissionTree")
    @ApiOperation("查询所有资源-获取的是树形数据")
    public ResultVo getPermissionTree(){
        List<SysPermission> list = sysPermissionService.getPermissionTree();
        if(list.size()!=0){
            return ResultUtils.success("获取树形资源数据成功!",list);
        }else {
            return ResultUtils.error("获取树形资源数据失败!",list);
        }
    }

    @SysLog("新增资源前,选择上级资源--去除按钮级别的树形资源数据")
    @GetMapping("/getParentPermissionTree")
    @ApiOperation("查询所有资源--去除按钮级别的树形资源数据")
    public ResultVo getParentPermissionTree(){
        List<SysPermission> list = sysPermissionService.getParentPermissionTree();
        if(list.size()!=0){
            return ResultUtils.success("获取树形资源数据成功!",list);
        }else {
            return ResultUtils.error("获取树形资源数据失败!",list);
        }
    }

    @SysLog("根据用户ID查询,该用户拥有的资源")
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

    @SysLog("根据角色ID查询,该角色拥有的资源")
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

