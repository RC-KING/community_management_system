package com.jdd.community_management_system.utils.dataUtils;

import lombok.Data;

@Data
public class ClubPageParam {
    // 页容量
    private int pageSize;
    // 当前页
    private int currentPage;
    // 社团名称搜索
    private String clubName;
}
