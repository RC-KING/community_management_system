package com.jdd.community_management_system.pojo.curriculum.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 社团课程表
 * </p>
 *
 * @author 金大大
 * @since 2022-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_curriculum")
@ApiModel(value="Curriculum对象", description="社团课程表")
public class Curriculum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属社团ID")
    private Long clubId;
    @ApiModelProperty(value = "所属社团名称")
    private String clubName;

    @ApiModelProperty(value = "负责人ID")
    private Long managerId;
    @ApiModelProperty(value = "负责人姓名")
    private String managerName;

    @ApiModelProperty(value = "助教ID")
    private Long assistantId;
    @ApiModelProperty(value = "助教姓名")
    private String assistantName;

    @ApiModelProperty(value = "指导老师ID")
    private Long leaderId;
    @ApiModelProperty(value = "指导老师姓名")
    private String leaderName;

    @ApiModelProperty(value = "常规课主题")
    private String title;

    @ApiModelProperty(value = "常规课记录")
    private String details;

    @ApiModelProperty(value = "课程任务")
    private String task;

    @ApiModelProperty(value = "课程地址")
    private String address;



    @ApiModelProperty(value = "实际人数")
    private Integer realNum;

    @ApiModelProperty(value = "请假人数")
    private Integer leaveNum;

    @ApiModelProperty(value = "旷到人数")
    private Integer absenceNum;

    @ApiModelProperty(value = "应到人数")
    private Integer totalNum;

    @ApiModelProperty(value = "上课时间")
    private Date beginTime;

    @ApiModelProperty(value = "下课时间")
    private Date endTime;

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
