package com.jdd.community_management_system.pojo.sys_log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author 金大大
 * @since 2022-01-28
 */
@Data
@TableName("t_sys_log")
@ApiModel(value="SysLog对象", description="日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志ID")
    @TableId
    private String logId;

    @ApiModelProperty(value = "0:正常 1：错误")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "请求地址")
    private String remoteAddr;

    @ApiModelProperty(value = "请求URL")
    private String requestUri;

    @ApiModelProperty(value = "请求方式")
    private String method;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "异常信息")
    private String exception;

    @ApiModelProperty(value = "操作人ID")
    private Long userId;

    @ApiModelProperty(value = "操作人姓名")
    private String userName;

    @ApiModelProperty(value = "返回参数")
    private String resultParams;

    @ApiModelProperty(value = "IP地址")
    private String ipNum;

    @ApiModelProperty(value = "IP区域")
    private String ipRegion;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;


}
