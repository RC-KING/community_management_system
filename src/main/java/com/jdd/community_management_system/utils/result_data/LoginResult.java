package com.jdd.community_management_system.utils.result_data;

import lombok.Data;

@Data
public class LoginResult {
  private int code;
  private String token;
  // token过期时间
  private Long expireTime;
  private Long id;
}
