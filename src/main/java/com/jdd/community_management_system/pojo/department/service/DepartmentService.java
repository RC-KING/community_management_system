package com.jdd.community_management_system.pojo.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.community_management_system.pojo.department.entity.Department;

import java.util.List;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-28
 */
public interface DepartmentService extends IService<Department> {
    //部门列表
    List<Department> getLeftTree(String deptName);
    //获取上级部门
    List<Department> getParent();

}
