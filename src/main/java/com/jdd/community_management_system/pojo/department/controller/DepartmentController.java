package com.jdd.community_management_system.pojo.department.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdd.community_management_system.pojo.department.entity.Department;
import com.jdd.community_management_system.pojo.department.service.DepartmentService;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import com.jdd.community_management_system.utils.log.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @SysLog("获取部门树形数据")
    @ApiOperation(value = "获取部门树形数据")
    @GetMapping("/getDepartmentList")
    //@SysLog(value ="查询部门树形列表")
    public ResultVo getLeftTree(String searchName){
        List<Department> leftTree = departmentService.getLeftTree(searchName);
        return ResultUtils.success("查询成功!",leftTree);
    }

    @SysLog("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    //@PreAuthorize("hasAuthority('sys:addDepartment')")
    public ResultVo addDept(@RequestBody Department department){
        if(departmentService.save(department)){
            return ResultUtils.success("新增部门成功!");
        }
        return ResultUtils.error("新增部门失败!");
    }

     @SysLog("编辑部门")
    @ApiOperation("编辑部门")
    @PatchMapping
    //@PreAuthorize("hasAuthority('sys:editDept')")
    public ResultVo editDept(@RequestBody Department department){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = departmentService.getById(department.getId()).getVersion();
        // 设置version字段
        department.setVersion(version);
        // 带上version字段进行更新
        if(departmentService.updateById(department)){
            return ResultUtils.success("编辑部门成功!",department);
        }
        return ResultUtils.error("编辑部门失败!",department);
    }

    @SysLog("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping("/{deptId}")
    // @PreAuthorize("hasAuthority('sys:deleteDept')")
    public ResultVo deleteDept(@PathVariable("deptId") String deptId){
        // 判断是否存在下级菜单
        QueryWrapper<Department> query = new QueryWrapper<>();
        query.eq("parent_id",deptId);
        List<Department> departments = departmentService.getBaseMapper().selectList(query);
        if (departments.size()>0){
            return ResultUtils.error("存在下级部门,删除部门失败!");
        }
        if(departmentService.removeById(deptId)){
            return ResultUtils.success("删除部门成功!");
        }
        return ResultUtils.error("删除部门失败!");
    }


    @SysLog("获取带有顶级部门的部门树")
    @ApiOperation("获取带有顶级部门的部门树")
    @GetMapping("/getParentTree")
    public ResultVo getParentDept(){
        List<Department> parent = departmentService.getParent();
        return ResultUtils.success("查询成功!",parent);
    }


}

