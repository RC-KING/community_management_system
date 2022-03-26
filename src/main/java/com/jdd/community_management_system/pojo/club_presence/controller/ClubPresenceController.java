package com.jdd.community_management_system.pojo.club_presence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.club.entity.Club;
import com.jdd.community_management_system.pojo.club.service.ClubService;
import com.jdd.community_management_system.pojo.club_presence.entity.ClubPresence;
import com.jdd.community_management_system.pojo.club_presence.service.ClubPresenceService;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.SysUserService;
import com.jdd.community_management_system.utils.dataUtils.PageParam;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 社团风采表 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-03-24
 */
@Api("社团风采表")
@RestController
@RequestMapping("/api/club_presence")
public class ClubPresenceController {

    @Autowired
    ClubPresenceService clubPresenceService;

    @PostMapping
    @ApiOperation("新增社团风采")
    //@PreAuthorize("hasAuthority('sys:clubPresence:add')")
    public ResultVo addClubPresence(@RequestBody ClubPresence clubPresence){
        if(clubPresenceService.save(clubPresence)){
            return ResultUtils.success("新增社团风采成功!",clubPresence);
        }else {
            return ResultUtils.error("新增社团风采失败!",clubPresence);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除社团风采")
    //@PreAuthorize("hasAuthority('sys:clubPresence:delete')")
    public ResultVo delClubPresence(@PathVariable Long id){
        if(clubPresenceService.removeById(id)){
            return ResultUtils.success("删除社团风采成功!",id);
        }else {
            return ResultUtils.error("删除社团风采失败!",id);
        }
    }
    @PatchMapping
    @ApiOperation("根据ID,修改社团风采")
    // @PreAuthorize("hasAuthority('sys:clubPresence:edit')")
    public ResultVo updateClubPresence(@RequestBody ClubPresence clubPresence){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = clubPresenceService.getById(clubPresence.getId()).getVersion();
        // 设置Version字段
        clubPresence.setVersion(version);
        // 采取更新措施
        if(clubPresenceService.updateById(clubPresence)){
            return ResultUtils.success("更新社团风采成功!",clubPresence);
        }else {
            return ResultUtils.error("更新社团风采失败!",clubPresence);
        }
    }

    @Autowired
    SysUserService sysUserService;
    @Autowired
    ClubService clubService;

    @ApiOperation(value="查询社团风采列表")
    @PostMapping("/list")
    public ResultVo list(@RequestBody PageParam clubPresencePageParam){
        QueryWrapper<ClubPresence> query = new QueryWrapper<>();
        // 根据角色名称(name)筛选查询
        if (StringUtils.isNotEmpty(clubPresencePageParam.getSearchKeyWord())) {
            query.lambda().like(ClubPresence::getTitle, clubPresencePageParam.getSearchKeyWord());
        }
        query.orderByAsc("order_num");
        IPage<ClubPresence> page = new Page<>();
        page.setCurrent(clubPresencePageParam.getCurrentPage());
        page.setSize(clubPresencePageParam.getPageSize());
        IPage<ClubPresence> clubPresenceIPage = clubPresenceService.getBaseMapper().selectPage(page, query);
        // 获取发布者姓名
        List<ClubPresence> records = clubPresenceIPage.getRecords();
        if (records.size()>0){
            for (ClubPresence record : records) {
                SysUser user = sysUserService.getById(record.getUserId());
                Club club = clubService.getById(record.getClubId());
                if (null!=user)record.setUserName(user.getUsername());
                if (null!=club)record.setClubName(club.getName());
            }
        }
        return ResultUtils.success("查询成功!",clubPresenceIPage);
    }
}

