package com.jdd.community_management_system.config.security.filter;


import com.jdd.community_management_system.config.security.detailservice.CustomerUserDetailsService;
import com.jdd.community_management_system.config.security.exception.CustomerAuthenticationException;
import com.jdd.community_management_system.config.security.exception.ImageCodeException;
import com.jdd.community_management_system.config.security.handler.LoginFailureHandler;
import com.jdd.community_management_system.utils.jwt.JwtUtils;
import com.jdd.community_management_system.utils.redis.RedisService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Data
@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {

  @Value("${permitUrl.loginUrl}") private String loginUrl;
  @Value("${permitUrl.imageCodeUrl}") private String imageCodeUrl;
  @Value("${permitUrl.registerUrl}") private String registerUrl;
  @Autowired private JwtUtils jwtUtils;
  @Autowired private LoginFailureHandler loginFailureHandler;
  @Autowired private CustomerUserDetailsService customerUserDetailsService;
  @Autowired private RedisService redisService;
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
          throws ServletException, IOException {
    // 放行swagger相关资源,不需要token认证
    if (StringUtils.contains(request.getServletPath(), "swagger")
            || StringUtils.contains(request.getServletPath(), "webjars")
            || StringUtils.contains(request.getServletPath(), "v3")
            || StringUtils.contains(request.getServletPath(), "profile")
            || StringUtils.contains(request.getServletPath(), "swagger-ui")
            || StringUtils.contains(request.getServletPath(), "swagger-resources")
            || StringUtils.contains(request.getServletPath(), "csrf")
            || StringUtils.contains(request.getServletPath(), "favicon")
            || StringUtils.contains(request.getServletPath(), "v2")) {
      filterChain.doFilter(request, response);
    } else {
      // 获取到请求的url
      String url = request.getRequestURI();
      try{
        // 如果是登录请求,才做验证验证码
        if (url.equals(loginUrl)) {
          validateYzm(request);
        }
        // 除了 登录请求 ,图片验证码请求,注册请求 之外,其余的请求都需要进行token验证
        if (!url.equals(loginUrl)&&
               !url.equals(imageCodeUrl)&&
               !url.equals(registerUrl)){
            validateToken(request);
        }
      }catch (AuthenticationException e){
        // 捕捉鉴权异常交给loginFailureHandler的onAuthenticationFailure异常处理
        loginFailureHandler.onAuthenticationFailure(request,response,e);
        return;
      }
      // 过滤器放行
      filterChain.doFilter(request,response);
    }
  }
  private void validateToken(HttpServletRequest request){
    // 从请求头的token字段中获取token
    String token = request.getHeader("token");
    // 从请求参数中获取token
    if(StringUtils.isEmpty(token)){
      token = request.getParameter("token");
    }
    // 从redis中获取token
    String tokenKey = "token_" + token;
    String redisToken = redisService.get(tokenKey);
    // 如果说redis中没有token(用户做过退出登录),则证明token已经失效
    if (StringUtils.isEmpty(redisToken)) {
      throw new CustomerAuthenticationException("未找到token,请重新登录!");
    }
    // 如果token不一致
    if (!token.equals(redisToken)) {
      throw new CustomerAuthenticationException("token验证失败!");
    }


    // 如果都没有获取到,则抛出异常
    if(StringUtils.isEmpty(token)){
      throw new CustomerAuthenticationException("token不存在！");
    }
    // 对获取到的token进行解析
    String username = jwtUtils.getUsernameFromToken(token);
    if(StringUtils.isEmpty(username)){
      throw new CustomerAuthenticationException("token解析失败!");
    }
    //获取用户信息
    UserDetails user = customerUserDetailsService.loadUserByUsername(username);
    if(user == null){
      throw new CustomerAuthenticationException("token验证失败!");
    }
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource()
            .buildDetails(request));
    //设置到spring security上下文
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }
  //验证验证码
  private void validateYzm(HttpServletRequest request){
    // 1. 获取验证码
    String userInputYzm = request.getParameter("code");
    // 2. 若用户输入为空
    if(StringUtils.isEmpty(userInputYzm)){
      throw new ImageCodeException("请输入验证码!");
    }
    // 对于制表符、换行符、换页符和回车符StringUtils.isBlank()均识为空白符
    if (StringUtils.isBlank(userInputYzm)) {
      throw new ImageCodeException("请输入正确的验证码!");
    }
    // 获取ip地址
    String ip = request.getRemoteAddr();
    // 获取redis中的验证码
    String redisYzm = redisService.get(ip + userInputYzm);
    // 如果redis中没有验证码
    if(StringUtils.isEmpty(redisYzm)){
      throw new ImageCodeException("验证码过期,请重新刷新!");
    }
    // 用户输入的验证码和redis中存储的验证码进行对比
    if(!userInputYzm.equalsIgnoreCase(redisYzm)){
      throw new ImageCodeException("验证码错误!");
    }

  }


}
