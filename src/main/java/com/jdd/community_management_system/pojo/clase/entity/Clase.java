package com.jdd.community_management_system.pojo.clase.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 班级
 * </p>
 *
 * @author 金大大
 * @since 2022-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_clase")
@ApiModel(value="Clase对象", description="班级")
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所在学院名称")
    private String collegeName;
    @ApiModelProperty(value = "所在学院ID")
    private Long collegeId;

    @ApiModelProperty(value = "所属专业名称")
    private String majorName;
    @ApiModelProperty(value = "所属专业ID")
    private Long majorId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "班级人数")
    private Integer studentNumber;

    @ApiModelProperty(value = "辅导员")
    private String adviser;

    @ApiModelProperty(value = "成立时间")
    private Date estabDate;

    @ApiModelProperty(value = "学习年数")
    private Integer yearNumber;

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


}
