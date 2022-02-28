package com.jdd.community_management_system.pojo.sys_log.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.sys_log.entity.SysLog;
import com.jdd.community_management_system.pojo.sys_log.service.SysLogService;
import com.jdd.community_management_system.utils.dataUtils.LogParam;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/api/sys_log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @com.jdd.community_management_system.utils.log.annotation.SysLog(value = "查询日志列表")
    @ApiOperation(value = "查询日志列表")
    @GetMapping
    public ResultVo list(@RequestBody LogParam logParam) {
        IPage<SysLog> rolePage = new Page<>(logParam.getCurrentPage(), logParam.getPageSize());
        QueryWrapper<SysLog> query = new QueryWrapper<>();
        query.lambda().orderByDesc(SysLog::getCreatedTime);
        IPage<SysLog> pageList = sysLogService.page(rolePage, query);
        return ResultUtils.success("查询成功!", pageList);
    }
}

