package com.jdd.community_management_system.config.security;

import com.jdd.community_management_system.config.security.detailservice.CustomerUserDetailsService;
import com.jdd.community_management_system.config.security.filter.CheckTokenFilter;
import com.jdd.community_management_system.config.security.handler.CustomAuthenticationEntryPoint;
import com.jdd.community_management_system.config.security.handler.LoginFailureHandler;
import com.jdd.community_management_system.config.security.handler.LoginSuccessHandler;
import com.jdd.community_management_system.config.security.handler.MyCustomAccessDenyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity // 启用Spring Security
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  // 自定义UserDetailsService 用于认证和授权
  @Autowired private CustomerUserDetailsService customUserDetailsService;
  // 登录成功之后的处理器
  @Autowired private LoginSuccessHandler loginSuccessHandler;
  // 登录失败之后的处理器
  @Autowired private LoginFailureHandler loginFailureHandler;
  // 匿名用户访问资源时处理器
  @Autowired private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  // 认证用户访问无权限资源时处理器
  @Autowired private MyCustomAccessDenyHandler CustomAccessDeniedHandler;
  //请求拦截器处理token
  @Autowired private CheckTokenFilter checkTokenFilter;

  @Value("${permitUrl.loginUrl}") private String loginUrl;
  @Value("${permitUrl.imageCodeUrl}") private String imageCodeUrl;
  @Value("${permitUrl.registerUrl}") private String registerUrl;

  // 加密/解密工具
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  /**
   * SpringSecurity核心配置 配置权限资源
   *
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // 在SpringSecurity的UsernamePasswordAuthenticationFilter执行之前进行Token验证
    http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
    http // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        .csrf()
        .disable()
        // 一个 .cors 就开启了 Spring Security 对 CORS 的支持。
        .cors()
        .and()
        // 和iframe支持
        .headers()
        .frameOptions()
        .disable()
        .and()
        .authorizeRequests()
        .requestMatchers(CorsUtils::isPreFlightRequest)
        .permitAll() // 处理跨域请求中的Preflight请求
        .and()
        .formLogin()
        // 登录请求的地址(不需要写在controller中,SpringSecurity中会处理这个请求地址,进行自己的用户认证)
        .loginProcessingUrl(loginUrl)
        // 自定义的登录验证成功或失败后的去向
        .successHandler(loginSuccessHandler)
        .failureHandler(loginFailureHandler)
        .and()
        // 前后端分离项目不需要Session(无状态)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // 放行路径
        .antMatchers(loginUrl, registerUrl,imageCodeUrl,"/api/**")
        .permitAll()
        // 大多都是swagger的资源
        .antMatchers(
            "/v3/**",
            "/profile/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            "/swagger-ui/**",
            "/v2/**",
            "/favicon.ico",
            "/webjars/springfox-swagger-ui/**",
            "/static/**",
            "/webjars/**",
            "/v2/api-docs",
            "/v2/feign-docs",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**")
        .permitAll()
        // 其他任何请求都需要权限
        .anyRequest()
        .authenticated()
        .and()
        // 配置两个异常处理
        .exceptionHandling()
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .accessDeniedHandler(CustomAccessDeniedHandler);
  }
  /** 配置认证处理器 自定义的UserDetailsService */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // 自定义认证类设置
    auth.userDetailsService(customUserDetailsService);
  }




}