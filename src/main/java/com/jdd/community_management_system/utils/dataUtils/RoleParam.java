package com.jdd.community_management_system.utils.dataUtils;

import lombok.Data;

@Data
// 用户(userId)给某个角色(roleId)分配权限
public class RoleParam {
    // 操作用户id
    private Long userId;
    // 选中角色的角色id
    private Long roleId;
}
