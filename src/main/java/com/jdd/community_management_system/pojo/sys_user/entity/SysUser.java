package com.jdd.community_management_system.pojo.sys_user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.jdd.community_management_system.pojo.sys_permission.entity.SysPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 金大大
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_user")
@ApiModel(value="SysUser对象", description="用户")
public class SysUser implements Serializable, UserDetails {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所在专业ID")
    private Long majorId;
    @TableField(exist = false)
    private String majorName;

    @ApiModelProperty(value = "所在学院ID")
    private Long collegeId;
    @TableField(exist = false)
    private String collegeName;

    @ApiModelProperty(value = "所在班级ID")
    private Long classId;
    @TableField(exist = false)
    private String className;

    @ApiModelProperty(value = "所属部门ID")
    private Long deptId;
    @TableField(exist = false)
    private String deptName;

    @ApiModelProperty(value = "所属社团ID")
    private Long clubId;
    @TableField(exist = false)
    private String clubName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "英文名")
    private String engName;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "手机号")
    private String mobilePhone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "月薪")
    private BigDecimal monthlySalary;

    @ApiModelProperty(value = "出生日期")
    private Date birth;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "身高")
    private Integer height;

    @ApiModelProperty(value = "体重")
    private Integer weight;

    @ApiModelProperty(value = "名族")
    private String nation;

    @ApiModelProperty(value = "政治面貌")
    private String political;

    @ApiModelProperty(value = "婚姻状况")
    private String marital;

    @ApiModelProperty(value = "籍贯（省）")
    private String domicilePlaceProvince;

    @ApiModelProperty(value = "籍贯（市）")
    private String domicilePlaceCity;

    @ApiModelProperty(value = "户籍地址")
    private String domicilePlaceAddress;

    @ApiModelProperty(value = "爱好")
    private String hobby;

    @ApiModelProperty(value = "简要介绍")
    private String intro;

    @ApiModelProperty(value = "居住地址")
    private String presentAddress;

    @ApiModelProperty(value = "入学日期")
    private Date entryDate;

    @ApiModelProperty(value = "系统登录用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "帐户是否过期;(1 未过期，0已过期)SpringSecurity字段")
    private boolean isAccountNonExpired =true;

    @ApiModelProperty(value = "帐户是否被锁定;(1 未锁定，0锁定)SpringSecurity字段")
    private boolean isAccountNonLocked =true;

    @ApiModelProperty(value = "密码是否过期;(1 未过期，0已过期)SpringSecurity字段")
    private boolean isCredentialsNonExpired =true;

    @ApiModelProperty(value = "帐户是否可用;(1 可用，0不可用)SpringSecurity字段")
    private boolean isEnabled =true;


    @ApiModelProperty(value = "管理员")
    private boolean isAdmin;

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

    //由于authorities不是数据库里面的字段，所以要排除他，不然mybatis-plus找不到该字段会报错
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    //用户权限列表,不属于用户表字段，需要排除
    @TableField(exist = false)
    List<SysPermission> permissionList;



}
