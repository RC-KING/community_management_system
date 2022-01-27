package com.jdd.community_management_system.utils.result_data;

import lombok.Data;

// 用于token刷新
@Data
public class TokenVo {
    private Long expireTime;
    private String token;
}
