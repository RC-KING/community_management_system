package com.jdd.community_management_system.pojo.club_presence.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 社团风采表
 * </p>
 *
 * @author 金大大
 * @since 2022-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_club_presence")
@ApiModel(value="ClubPresence对象", description="社团风采表")
public class ClubPresence implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "社团ID")
    private Long clubId;

    @ApiModelProperty(value = "发布人ID")
    private Long userId;

    @ApiModelProperty(value = "社团名称")
    private String  clubName;

    @ApiModelProperty(value = "发布人姓名")
    private String userName;

    @ApiModelProperty(value = "主题")
    private String title;

    @ApiModelProperty(value = "风采详情")
    private String details;

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
