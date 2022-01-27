package com.jdd.community_management_system.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  /** 跨域配置 */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("*")
        .allowedHeaders("*")
            // 前端服务器的域名(允许跨域的域名,千万不要图方便写个"*",会报错!)
        .allowedOrigins("http://localhost:3000")
        .allowCredentials(true);
  }
}
