package com.jdd.community_management_system.pojo.activity.service.impl;

import com.jdd.community_management_system.pojo.activity.entity.Activity;
import com.jdd.community_management_system.pojo.activity.mapper.ActivityMapper;
import com.jdd.community_management_system.pojo.activity.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 社团活动表 服务实现类
 * </p>
 *
 * @author 金大大
 * @since 2022-03-15
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

}
