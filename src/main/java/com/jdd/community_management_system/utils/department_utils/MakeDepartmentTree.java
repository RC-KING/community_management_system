package com.jdd.community_management_system.utils.department_utils;

import com.jdd.community_management_system.pojo.department.entity.Department;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeDepartmentTree {
    public static List<Department> makeDepartmentTree(List<Department> deptList,Long pid){
        // 这个list是存放整个部门树形数据的
        List<Department> list = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId() == pid)
                .forEach(dept -> {
                    Department department = new Department();
                    // 将每一项数据进行深拷贝
                    BeanUtils.copyProperties(dept, department);
                    List<Department> children = makeDepartmentTree(deptList,dept.getId());
                    department.setChildren(children);
                    list.add(department);
                });
        return list;
    }
}
