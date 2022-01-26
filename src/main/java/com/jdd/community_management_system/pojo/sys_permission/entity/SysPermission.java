package com.jdd.community_management_system.pojo.sys_permission.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_permission")
@ApiModel(value="SysPermission对象", description="权限")
public class SysPermission implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "权限ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父权限ID")
    private Long parentId;

    @ApiModelProperty(value = "权限名称")
    private String label;

    @ApiModelProperty(value = "权限代码(按钮级控制)")
    private String code;

    @ApiModelProperty(value = "Vue组件存放路径")
    private String path;

    @ApiModelProperty(value = "Vue组件名称")
    private String name;

    @ApiModelProperty(value = "Vue组件路由地址")
    private String url;

    @ApiModelProperty(value = "排序序号")
    private Integer orderNum;

    @ApiModelProperty(value = "类型(0 目录 1菜单，2按钮)")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "权限备注")
    private String remark;

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
