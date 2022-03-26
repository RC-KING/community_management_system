package com.jdd.community_management_system.pojo.notice.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author 金大大
 * @since 2022-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_notice")
@ApiModel(value="Notice对象", description="公告表")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发布者ID")
    private Long userId;

    @ApiModelProperty(value = "发布者姓名")
    private String creator;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "公告简介")
    private String intro;

    @ApiModelProperty(value = "公告详情")
    private String details;

    @ApiModelProperty(value = "公告排序")
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



}
