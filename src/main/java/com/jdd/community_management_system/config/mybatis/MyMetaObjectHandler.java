package com.jdd.community_management_system.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component // 一定不要忘记将处理器交给Spring容器
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时候的策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("createdTime",new Date(),metaObject);
        this.setFieldValByName("updatedTime",new Date(),metaObject);
    }
    // 更新时候的策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        // 给实体的updateTime字段添加值为new Date()
        this.setFieldValByName("updatedTime",new Date(),metaObject);
    }
}