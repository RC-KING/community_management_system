package com.jdd.community_management_system.pojo.sys_user_role.entity;

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
 * 用户/角色表
 * </p>
 *
 * @author 金大大
 * @since 2022-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_user_role")
@ApiModel(value="SysUserRole对象", description="用户/角色表")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户_角色表ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;


}
