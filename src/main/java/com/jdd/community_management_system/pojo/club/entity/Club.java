package com.jdd.community_management_system.pojo.club.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 社团
 * </p>
 *
 * @author 金大大
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_club")
@ApiModel(value="Club对象", description="社团")
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "社团ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "社团名称")
    private String name;

    @ApiModelProperty(value = "社团口号")
    private String slogan;

    @ApiModelProperty(value = "社团LOGO")
    private String logo;

    @ApiModelProperty(value = "社团简介")
    private String intro;

    @ApiModelProperty(value = "社团详情")
    private String details;

    @ApiModelProperty(value = "社团地址")
    private String address;

    @ApiModelProperty(value = "社长名称")
    private String managerName;

    @ApiModelProperty(value = "指导老师")
    private String guideTeacher;

    @ApiModelProperty(value = "指导老师电话")
    private String guideTeacherPhone;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;


}
