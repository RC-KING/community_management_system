package com.jdd.community_management_system.pojo.sys_log.service;

import com.jdd.community_management_system.pojo.sys_log.entity.SysLog;
import org.springframework.stereotype.Service;

@Service
public interface SysLogTask {
  // 保存日志
  void saveSysLog(SysLog sysLogEntity);
  // 更新日志
  void updateSysLog(SysLog sysLogEntity);
}
