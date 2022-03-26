package com.jdd.community_management_system.utils.dataUtils;

import lombok.Data;

@Data
public class PageParam {
    // 页容量
    private int pageSize;
    // 当前页
    private int currentPage;
    // 模糊搜索
    private String SearchKeyWord;
}
