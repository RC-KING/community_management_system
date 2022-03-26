package com.jdd.community_management_system.pojo.curriculum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.club.entity.Club;
import com.jdd.community_management_system.pojo.club.service.ClubService;
import com.jdd.community_management_system.pojo.curriculum.entity.Curriculum;
import com.jdd.community_management_system.pojo.curriculum.service.CurriculumService;
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
 * 社团常规课表 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/api/curriculum")
public class CurriculumController {
    @Autowired
    CurriculumService curriculumService;

    @PostMapping
    @ApiOperation("新增常规课")
    //@PreAuthorize("hasAuthority('sys:curriculum:add')")
    public ResultVo addCurriculum(@RequestBody Curriculum curriculum){
        if(curriculumService.save(curriculum)){
            return ResultUtils.success("新增常规课成功!",curriculum);
        }else {
            return ResultUtils.error("新增常规课失败!",curriculum);
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除常规课")
    //@PreAuthorize("hasAuthority('sys:curriculum:delete')")
    public ResultVo delCurriculum(@PathVariable Long id){
        if(curriculumService.removeById(id)){
            return ResultUtils.success("删除常规课成功!",id);
        }else {
            return ResultUtils.error("删除常规课失败!",id);
        }
    }
    @PatchMapping
    @ApiOperation("根据ID,修改常规课")
    // @PreAuthorize("hasAuthority('sys:curriculum:edit')")
    public ResultVo updateCurriculum(@RequestBody Curriculum curriculum){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = curriculumService.getById(curriculum.getId()).getVersion();
        // 设置Version字段
        curriculum.setVersion(version);
        // 采取更新措施
        if(curriculumService.updateById(curriculum)){
            return ResultUtils.success("更新常规课成功!",curriculum);
        }else {
            return ResultUtils.error("更新常规课失败!",curriculum);
        }
    }

    @Autowired
    SysUserService sysUserService;
    @Autowired
    ClubService clubService;

    @ApiOperation(value="查询常规课列表")
    @PostMapping("/list")
    public ResultVo list(@RequestBody PageParam curriculumPageParam){
        QueryWrapper<Curriculum> query = new QueryWrapper<>();
        // 根据常规课title或intro筛选查询
        if (StringUtils.isNotEmpty(curriculumPageParam.getSearchKeyWord())) {
            query.lambda().like(Curriculum::getTitle, curriculumPageParam.getSearchKeyWord());
        }
        IPage<Curriculum> page = new Page<>();
        page.setCurrent(curriculumPageParam.getCurrentPage());
        page.setSize(curriculumPageParam.getPageSize());
        IPage<Curriculum> curriculumIPage = curriculumService.getBaseMapper().selectPage(page, query);
        // 回显 name
        List<Curriculum> records = curriculumIPage.getRecords();
        if (records.size()>0){
            for (Curriculum record : records) {
                SysUser assistant = sysUserService.getById(record.getAssistantId());
                SysUser manager = sysUserService.getById(record.getManagerId());
                SysUser leader = sysUserService.getById(record.getLeaderId());
                Club club = clubService.getById(record.getClubId());
                if (assistant!=null) record.setAssistantName(assistant.getUsername());
                if (manager!=null) record.setManagerName(manager.getUsername());
                if (leader!=null) record.setLeaderName(leader.getUsername());
                if (club!=null) record.setClubName(club.getName());
            }
        }


        return ResultUtils.success("查询成功!",curriculumIPage);
    }
}

