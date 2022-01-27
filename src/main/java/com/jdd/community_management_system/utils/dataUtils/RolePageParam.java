package com.jdd.community_management_system.utils.dataUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RolePageParam {
    // 页容量
    @ApiModelProperty(name = "pageSize", value = "页容量", required = true)
    private int pageSize;
    // 当前页
    @ApiModelProperty(name = "currentPage", value = "当前页", required = true)
    private int currentPage;
    // 角色名称
    private String name;
    // 用户id
    @ApiModelProperty(name = "userId", value = "用户id", required = true)
    private Long userId;
}
