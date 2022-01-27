package com.jdd.community_management_system.utils.dataUtils;

import lombok.Data;

import java.util.List;
@Data
public class RolePermissionParam {
    private Long roleId;
    private List<Long> permissionIdList;
}