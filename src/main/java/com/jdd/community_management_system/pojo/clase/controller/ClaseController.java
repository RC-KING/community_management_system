package com.jdd.community_management_system.pojo.clase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdd.community_management_system.pojo.clase.entity.Clase;
import com.jdd.community_management_system.pojo.clase.service.ClaseService;
import com.jdd.community_management_system.utils.dataUtils.PageParam;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 班级 前端控制器
 * </p>
 *
 * @author 金大大
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/api/class")
public class ClaseController {
    @Autowired
    ClaseService claseService;

    @PostMapping
    @ApiOperation("新增班级")
    //@PreAuthorize("hasAuthority('sys:clase:add')")
    public ResultVo addClase(@RequestBody Clase clase){
        if(claseService.save(clase)){
            return ResultUtils.success("新增班级成功!",clase);
        }else {
            return ResultUtils.error("新增班级失败!",clase);
        }
    }
    // TODO:判断级下的学生是否完全删除
    @DeleteMapping("/{id}")
    @ApiOperation("根据ID,删除单个删除班级")
    //@PreAuthorize("hasAuthority('sys:clase:delete')")
    public ResultVo delClase(@PathVariable Long id){
        if(claseService.removeById(id)){
            return ResultUtils.success("删除班级成功!",id);
        }else {
            return ResultUtils.error("删除班级失败!",id);
        }
    }

    @PatchMapping
    @ApiOperation("根据ID,修改班级")
    // @PreAuthorize("hasAuthority('sys:clase:edit')")
    public ResultVo updateClase(@RequestBody Clase clase){
        // 查询乐观锁,首先得查询出来Version字段
        Integer version = claseService.getById(clase.getId()).getVersion();
        // 设置Version字段
        clase.setVersion(version);
        // 采取更新措施
        if(claseService.updateById(clase)){
            return ResultUtils.success("更新班级成功!",clase);
        }else {
            return ResultUtils.error("更新班级失败!",clase);
        }
    }

    @ApiOperation(value="查询班级列表")
    @PostMapping("/list")
    public ResultVo list(@RequestBody PageParam clasePageParam){
        QueryWrapper<Clase> query = new QueryWrapper<>();
        // 根据角色名称(name)筛选查询
        if (StringUtils.isNotEmpty(clasePageParam.getSearchKeyWord())) {
            query.lambda().like(Clase::getClassName, clasePageParam.getSearchKeyWord());
        }
        IPage<Clase> page = new Page<>();
        page.setCurrent(clasePageParam.getCurrentPage());
        page.setSize(clasePageParam.getPageSize());
        IPage<Clase> claseIPage = claseService.getBaseMapper().selectPage(page, query);
        return ResultUtils.success("查询成功!",claseIPage);
    }
}

