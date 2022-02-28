package com.jdd.community_management_system.pojo.department.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.community_management_system.pojo.department.entity.Department;
import com.jdd.community_management_system.pojo.department.mapper.DepartmentMapper;
import com.jdd.community_management_system.pojo.department.service.DepartmentService;
import com.jdd.community_management_system.utils.department_utils.MakeDepartmentTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author 金大大
 * @since 2022-01-28
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public List<Department> getLeftTree(String deptName) {
        QueryWrapper<Department> query = new QueryWrapper<>();
        // 根据deptName进行筛选查询
        if(StringUtils.isNotEmpty(deptName)){
            query.lambda().like(Department::getName,deptName);
        }
        query.lambda().orderByAsc(Department::getOrderNum);
        List<Department> departments = this.baseMapper.selectList(query);
        List<Department> departmentList = null;
        // 如果有数据则进行树形数据的构造
        if(departments.size() >0){
            departmentList = MakeDepartmentTree.makeDepartmentTree(departments, 0L);
        }
        return departmentList;
    }

    @Override
    public List<Department> getParent() {
        QueryWrapper<Department> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Department::getOrderNum);
        List<Department> departments = this.baseMapper.selectList(query);
        // 构造顶级部门
        Department department = new Department();
        department.setId(0L);
        department.setParentId(-1L);
        department.setName("顶级部门");
        department.setLikeId("0");
        // 将顶级部门添加到查询到的部门列表中
        departments.add(department);
        // 生成带有顶级部门的部门树
        return MakeDepartmentTree.makeDepartmentTree(departments, -1L);

    }
}
