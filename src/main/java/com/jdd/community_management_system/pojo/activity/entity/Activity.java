package com.jdd.community_management_system.pojo.activity.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 社团活动表
 * </p>
 *
 * @author 金大大
 * @since 2022-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_activity")
@ApiModel(value="Activity对象", description="社团活动表")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "举办活动社团ID")
    private Long clubId;
    @ApiModelProperty(value = "举办活动社团名称")
    private String clubName;

    @ApiModelProperty(value = "活动物资ID")
    private Long suppliesId;
    @ApiModelProperty(value = "活动物资名称")
    private String suppliesName;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "活动规则")
    private String rule;

    @ApiModelProperty(value = "活动简介")
    private String intro;

    @ApiModelProperty(value = "活动详情")
    private String details;

    @ApiModelProperty(value = "活动地点")
    private String address;

    @ApiModelProperty(value = "实际人数")
    private Integer realNum;

    @ApiModelProperty(value = "请假人数")
    private Integer leaveNum;

    @ApiModelProperty(value = "旷到人数")
    private Integer absenceNum;

    @ApiModelProperty(value = "应到人数")
    private Integer totalNum;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "活动策划方案")
    private String planningCases;

    @ApiModelProperty(value = "活动负责人")
    private String manager;

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
