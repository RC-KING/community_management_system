package com.jdd.community_management_system.pojo.department.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author 金大大
 * @since 2022-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_department")
@ApiModel(value="Department对象", description="部门")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级部门ID")
    private Long parentId;

    @ApiModelProperty(value = "上级部门ID")
    private String parentName;

    @ApiModelProperty(value = "所属社团ID")
    private Long clubId;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门代码")
    private String code;

    @ApiModelProperty(value = "部门电话")
    private String phone;

    @ApiModelProperty(value = "部门地址")
    private String address;

    @ApiModelProperty(value = "部门管理员")
    private String manager;

    @ApiModelProperty(value = "部门简介")
    private String intro;

    @ApiModelProperty(value = "总人数")
    private Integer totalNum;

    @ApiModelProperty(value = "上级部门id及自己的id集合")
    private String likeId;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @TableField(exist = false)
    private Boolean open;

    //树的子级
    @TableField(exist = false)
    private List<Department> children = new ArrayList<>();


}
