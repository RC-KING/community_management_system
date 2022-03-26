package com.jdd.community_management_system.pojo.activity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.activity.entity.Activity;
import com.jdd.community_management_system.pojo.activity.service.ActivityService;
import com.jdd.community_management_system.pojo.club.entity.Club;
import com.jdd.community_management_system.pojo.club.service.ClubService;
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
 * 活动活动表 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @PostMapping
    @ApiOperation("新增活动")
    //@PreAuthorize("hasAuthority('sys:activity:add')")
    public ResultVo addActivity(@RequestBody Activity activity){
        if(activityService.save(activity)){
            return ResultUtils.success("新增活动成功!",activity);
        }else {
            return ResultUtils.error("新增活动失败!",activity);
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除活动")
    //@PreAuthorize("hasAuthority('sys:activity:delete')")
    public ResultVo delActivity(@PathVariable Long id){
        if(activityService.removeById(id)){
            return ResultUtils.success("删除活动成功!",id);
        }else {
            return ResultUtils.error("删除活动失败!",id);
        }
    }
    @PatchMapping
    @ApiOperation("根据ID,修改活动")
    // @PreAuthorize("hasAuthority('sys:activity:edit')")
    public ResultVo updateActivity(@RequestBody Activity activity){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = activityService.getById(activity.getId()).getVersion();
        // 设置Version字段
        activity.setVersion(version);
        // 采取更新措施
        if(activityService.updateById(activity)){
            return ResultUtils.success("更新活动成功!",activity);
        }else {
            return ResultUtils.error("更新活动失败!",activity);
        }
    }

    @Autowired
    ClubService clubService;

    @ApiOperation(value="查询活动列表")
    @PostMapping("/list")
    public ResultVo list(@RequestBody PageParam activityPageParam){
        QueryWrapper<Activity> query = new QueryWrapper<>();
        // 根据活动title或intro筛选查询
        if (StringUtils.isNotEmpty(activityPageParam.getSearchKeyWord())) {
            query.lambda().like(Activity::getTitle, activityPageParam.getSearchKeyWord())
                    .or().like(Activity::getIntro, activityPageParam.getSearchKeyWord());
        }
        IPage<Activity> page = new Page<>();
        page.setCurrent(activityPageParam.getCurrentPage());
        page.setSize(activityPageParam.getPageSize());
        IPage<Activity> activityIPage = activityService.getBaseMapper().selectPage(page, query);
        // 回显clubName和 suppliesName
        List<Activity> records = activityIPage.getRecords();
        if (records.size()>0){
          for (Activity record : records) {
              Long clubId = record.getClubId();
              Club clubById = clubService.getById(clubId);
              if (clubById!=null){
                  record.setClubName(clubById.getName());
              }


              // TODO:物资的name回显
              // Long suppliesId = record.getSuppliesId();

          }
        }


        return ResultUtils.success("查询成功!",activityIPage);
    }
}

