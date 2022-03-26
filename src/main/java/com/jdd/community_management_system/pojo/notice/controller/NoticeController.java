package com.jdd.community_management_system.pojo.notice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.notice.entity.Notice;
import com.jdd.community_management_system.pojo.notice.service.NoticeService;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.SysUserService;
import com.jdd.community_management_system.utils.dataUtils.PageParam;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @PostMapping
    @ApiOperation("新增公告")
    //@PreAuthorize("hasAuthority('sys:notice:add')")
    public ResultVo addNotice(@RequestBody Notice notice){
        if(noticeService.save(notice)){
            return ResultUtils.success("新增公告成功!",notice);
        }else {
            return ResultUtils.error("新增公告失败!",notice);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除公告")
    //@PreAuthorize("hasAuthority('sys:notice:delete')")
    public ResultVo delNotice(@PathVariable Long id){
        if(noticeService.removeById(id)){
            return ResultUtils.success("删除公告成功!",id);
        }else {
            return ResultUtils.error("删除公告失败!",id);
        }
    }
    @PatchMapping
    @ApiOperation("根据ID,修改公告")
    // @PreAuthorize("hasAuthority('sys:notice:edit')")
    public ResultVo updateNotice(@RequestBody Notice notice){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = noticeService.getById(notice.getId()).getVersion();
        // 设置Version字段
        notice.setVersion(version);
        // 采取更新措施
        if(noticeService.updateById(notice)){
            return ResultUtils.success("更新公告成功!",notice);
        }else {
            return ResultUtils.error("更新公告失败!",notice);
        }
    }

    @Autowired
    SysUserService sysUserService;

    @ApiOperation(value="查询公告列表")
    @PostMapping("/list")
    public ResultVo list(@RequestBody PageParam noticePageParam){
        QueryWrapper<Notice> query = new QueryWrapper<>();
        // 根据角色名称(name)筛选查询
        if (StringUtils.isNotEmpty(noticePageParam.getSearchKeyWord())) {
            query.lambda().like(Notice::getTitle, noticePageParam.getSearchKeyWord())
                    .or().like(Notice::getIntro,  noticePageParam.getSearchKeyWord());
        }
        query.orderByAsc("order_num");
        IPage<Notice> page = new Page<>();
        page.setCurrent(noticePageParam.getCurrentPage());
        page.setSize(noticePageParam.getPageSize());
        IPage<Notice> noticeIPage = noticeService.getBaseMapper().selectPage(page, query);
        // 获取创建者姓名
        List<Notice> records = noticeIPage.getRecords();
        if (records.size()>0){
            for (Notice record : records) {
                SysUser user = sysUserService.getById(record.getUserId());
                record.setCreator(user.getUsername());
            }
        }
        return ResultUtils.success("查询成功!",noticeIPage);
    }
}

