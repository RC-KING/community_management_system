package com.jdd.community_management_system.pojo.sys_role_permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色/权限表
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_role_permission")
@ApiModel(value="SysRolePermission对象", description="角色/权限表")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色_权限表ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "权限ID")
    private Long permissionId;


}
