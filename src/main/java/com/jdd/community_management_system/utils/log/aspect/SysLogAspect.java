package com.jdd.community_management_system.utils.log.aspect;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.jdd.community_management_system.pojo.sys_log.entity.SysLog;
import com.jdd.community_management_system.pojo.sys_log.service.SysLogTask;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.utils.log.utils.DateUtils;
import com.jdd.community_management_system.utils.log.utils.IPHelper;
import com.jdd.community_management_system.utils.log.utils.JsonToBeanUtils;
import com.jdd.community_management_system.utils.log.utils.UUIDUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/** @Aspect 注解用来描述一个切面类，定义切面类的时候需要打上这个注解。 @Component 注解将该类交给 Spring 来管理 */
@Aspect // 表明该类是一个切面类，是切点和通知的集合
@Component // 把该类交给spring管理
public class SysLogAspect {
  // 每个用户都是一个线程,这个保证每个用户的线程是互不干扰的
  // 将SysLogEntity对象存入这个里面,是为了让后面的一些方法能够直接操作这个对象
  private static final ThreadLocal<SysLog> threadLog = new NamedThreadLocal<>("log");

  @Autowired private HttpServletRequest request;
  // @Autowired private SysLogService sysLogService;
  @Autowired private SysLogTask sysLogTask;

  /** 定义切点 */
  @Pointcut("@annotation(com.jdd.community_management_system.utils.log.annotation.SysLog)")
  public void pointAspect() {}

  /**
   * 前置通知：方法执行之前处理
   *
   * @param joinPoint
   */
  @Before("pointAspect()")
  public void doBefore(JoinPoint joinPoint) {
    // 开始执行时间
    Date beginTime = new Date();
    // 定义日志实体
    SysLog sysLog = new SysLog();
    sysLog.setLogId(UUIDUtil.getUniqueIdByUUId());
    // 获取操作用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    SysUser user = (SysUser) authentication.getPrincipal();
    sysLog.setUserId(user.getId());
    sysLog.setUserName(user.getUsername());
    sysLog.setStartTime(beginTime);
    // 获取ip
    sysLog.setIpNum(IPHelper.getIpAddr(request));
    sysLog.setRemoteAddr(request.getRemoteAddr());
    sysLog.setCreatedTime(new Date());
    // 保存日志
    sysLogTask.saveSysLog(sysLog);
    threadLog.set(sysLog);
  }

  /**
   * 后置通知：方法执行之后处理
   *
   * @param joinPoint
   */
  @After("pointAspect()")
  public void doAfter(JoinPoint joinPoint) {
    SysLog logEntity = threadLog.get();
    // 获取请求的方法
    String name = joinPoint.getSignature().getName();
    logEntity.setMethod(request.getMethod() + "-----" + name + "()");
    logEntity.setRequestUri(request.getRequestURI());
    // 获取切入点所在的方法
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    com.jdd.community_management_system.utils.log.annotation.SysLog annotation = method.getAnnotation(com.jdd.community_management_system.utils.log.annotation.SysLog.class);
    if (annotation != null) {
      logEntity.setTitle(annotation.value());
    }
    // 获取请求的参数
    Object[] args = joinPoint.getArgs();
    int length = args.length;
    if (length > 0) {
      String jsonString = JsonToBeanUtils.toJsonString(args[0]);
      // 设置请求参数
      logEntity.setParams(jsonString);
    }
    // 正常情况的默认值(没有出异常)
    logEntity.setType("0");
    Long endTime = SystemClock.now();
    // 设置结束时间
    logEntity.setEndTime(DateUtils.parseDate(endTime));
    // 更新
    sysLogTask.updateSysLog(logEntity);
  }

  /**
   * 成功返回时的通知 : 成功返回处理
   *
   * @param res
   */
  @AfterReturning(returning = "res", pointcut = "pointAspect()")
  public void doReturn(Object res) {
    // res 是成功操作返回的参数
    SysLog logEntity = threadLog.get();
    if (logEntity != null) {
      logEntity.setResultParams(JsonToBeanUtils.toJsonString(res));
      // 更新
      sysLogTask.updateSysLog(logEntity);
    }
  }

  /**
   * 异常时的通知 : 异常处理
   *
   * @param joinPoint
   * @param e
   */
  @AfterThrowing(pointcut = "pointAspect()", throwing = "e")
  public void doException(JoinPoint joinPoint, Throwable e) {
    SysLog logEntity = threadLog.get();
    if (logEntity != null) {
      logEntity.setType("1");
      // 记录异常信息到数据库
      logEntity.setException(e.toString());
      // 更新
      sysLogTask.updateSysLog(logEntity);
    }
  }
}
