package com.jdd.community_management_system.config.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 标注为一个配置类
@Configuration
// 启用swagger
@EnableOpenApi
public class SwaggerConfig {
  // 核心配置类
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.OAS_30)
        // 文档配置
        .apiInfo(apiInfo())
        .securitySchemes(securitySchemes())
        .securityContexts(securityContexts())
        .select()
        // 为当前包路径
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .paths(PathSelectors.any())
        .build();
  }
  // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        // 页面标题
        .title("高校社团管理系统API文档")
        // 版本号
        .version("1.0")
        // 描述
        .description("API文档")
        .build();
  }

  private List<SecurityScheme> securitySchemes() {
    ApiKey apiKey = new ApiKey("token", "token", "header");
    return Collections.singletonList(apiKey);
  }

  private List<SecurityContext> securityContexts() {
    List<SecurityContext> securityContexts = new ArrayList<>();
    securityContexts.add(
        SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("^(?!auth).*$"))
            .build());
    return securityContexts;
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    List<SecurityReference> securityReferences = new ArrayList<>();
    securityReferences.add(new SecurityReference("token", authorizationScopes));
    return securityReferences;
  }
}
