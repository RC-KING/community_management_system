package com.jdd.community_management_system.pojo.sys_log.service.impl;


import com.jdd.community_management_system.pojo.sys_log.entity.SysLog;
import com.jdd.community_management_system.pojo.sys_log.service.SysLogService;
import com.jdd.community_management_system.pojo.sys_log.service.SysLogTask;
import com.jdd.community_management_system.utils.log.utils.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SysLogTaskImpl implements SysLogTask {
  @Autowired private SysLogService sysLogService;

  @Autowired private AddressUtils addressUtils;

  @Async(value = "threadPoolTaskExecutor") // 使用多线程
  @Override
  public void saveSysLog(SysLog sysLogEntity) {
    String ipRegion = addressUtils.getRealAddressByIP(sysLogEntity.getRemoteAddr());
    sysLogEntity.setIpRegion(ipRegion);
    sysLogService.save(sysLogEntity);
  }

  @Async(value = "threadPoolTaskExecutor")
  @Override
  public void updateSysLog(SysLog sysLogEntity) {
    sysLogService.updateById(sysLogEntity);
  }
}
