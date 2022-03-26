package com.jdd.community_management_system.pojo.club.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.club.entity.Club;
import com.jdd.community_management_system.pojo.club.service.ClubService;
import com.jdd.community_management_system.utils.dataUtils.PageParam;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 社团 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/api/club")
public class ClubController {
    @Autowired
    ClubService clubService;
    
    @PostMapping
    @ApiOperation("新增社团")
    //@PreAuthorize("hasAuthority('sys:club:add')")
    public ResultVo addClub(@RequestBody Club club){
        if(clubService.save(club)){
            return ResultUtils.success("新增社团成功!",club);
        }else {
            return ResultUtils.error("新增社团失败!",club);
        }
    }
    // TODO:判断社团下的部门是否完全删除
    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除社团")
    //@PreAuthorize("hasAuthority('sys:club:delete')")
    public ResultVo delClub(@PathVariable Long id){
        if(clubService.removeById(id)){
            return ResultUtils.success("删除社团成功!",id);
        }else {
            return ResultUtils.error("删除社团失败!",id);
        }
    }
    @PatchMapping
    @ApiOperation("根据ID,修改社团")
    // @PreAuthorize("hasAuthority('sys:club:edit')")
    public ResultVo updateClub(@RequestBody Club club){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = clubService.getById(club.getId()).getVersion();
        // 设置Version字段
        club.setVersion(version);
        // 采取更新措施
        if(clubService.updateById(club)){
            return ResultUtils.success("更新社团成功!",club);
        }else {
            return ResultUtils.error("更新社团失败!",club);
        }
    }

    @ApiOperation(value="查询社团列表")
    @PostMapping("/list")
    public ResultVo list(@RequestBody PageParam clubPageParam){
        QueryWrapper<Club> query = new QueryWrapper<>();
        // 根据角色名称(name)筛选查询
        if (StringUtils.isNotEmpty(clubPageParam.getSearchKeyWord())) {
            query.lambda().like(Club::getName, clubPageParam.getSearchKeyWord());
        }
        IPage<Club> page = new Page<>();
        page.setCurrent(clubPageParam.getCurrentPage());
        page.setSize(clubPageParam.getPageSize());
        IPage<Club> clubIPage = clubService.getBaseMapper().selectPage(page, query);
        return ResultUtils.success("查询成功!",clubIPage);
    }
}

