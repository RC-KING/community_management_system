package com.jdd.community_management_system.config.security.filter;


import com.jdd.community_management_system.config.security.detailservice.CustomerUserDetailsService;
import com.jdd.community_management_system.config.security.exception.CustomerAuthenticationException;
import com.jdd.community_management_system.config.security.handler.LoginFailureHandler;
import com.jdd.community_management_system.utils.jwt.JwtUtils;
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
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // 获取到请求的url
    String url = request.getRequestURI();
    try{
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

  private void validateToken(HttpServletRequest request){
    // 从请求头的token字段中获取token
    String token = request.getHeader("token");
    // 从请求头部的Authorization获取到token
    if(StringUtils.isEmpty(token)){
      token = request.getHeader("Authorization");
    }
    // 从请求参数中获取token
    if(StringUtils.isEmpty(token)){
      token = request.getParameter("token");
    }
    // 从redis中获取token
    // ...


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

//  @Value("${itmk.loginUrl}")
//  private String loginUrl;
//
//  @Value("${jdd.imageCodeUrl}")
//  private String imageCodeUrl;
//
//  @Autowired private JwtUtils jwtUtils;
//  @Autowired private LoginFailureHandler loginFailureHandler;
//  @Autowired private CustomerUserDetailsService customerUserDetailsService;
////  @Autowired private RedisService redisService;
//
//  @Override
//  protected void doFilterInternal(
//      HttpServletRequest httpServletRequest,
//      HttpServletResponse httpServletResponse,
//      FilterChain filterChain)
//      throws ServletException, IOException {
//
//    // 放行swagger相关资源,不需要token认证
//    if (StringUtils.contains(httpServletRequest.getServletPath(), "swagger")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "webjars")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "v3")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "profile")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "swagger-ui")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "swagger-resources")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "csrf")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "favicon")
//        || StringUtils.contains(httpServletRequest.getServletPath(), "v2")) {
//      filterChain.doFilter(httpServletRequest, httpServletResponse);
//    } else {
//      try {
//        // 获取请求的url
//        String url = httpServletRequest.getRequestURI();
//        // 如果是登录请求,才做验证验证码
//        if (url.equals(loginUrl)) {
//          validateImage(httpServletRequest);
//        }
//        // 除了 登录请求 和 验证码请求 之外,其余的请求都需要进行token验证
//        if (!url.equals(loginUrl) && !url.equals(imageCodeUrl)) {
//          // token验证
//          validateToken(httpServletRequest);
//        }
//      } catch (AuthenticationException e) {
//        // 异常处理交给 LoginFailureHandler(登陆失败处理器)
//        loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
//        return;
//      }
//      // 放行过滤器
//      filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//  }
//  // token验证
//  private void validateToken(HttpServletRequest request) {
//    // 从请求的头部获取token
//    String token = request.getHeader("token");
//    // 如果请求头部没有获取到token，则从请求参数中获取token
//    if (StringUtils.isEmpty(token)) {
//      token = request.getParameter("token");
//    }
//    // 如果请求参数中也没有获取到token
//    if (StringUtils.isEmpty(token)) {
//      throw new CustomerAuthorizationException("token不存在!");
//    }
//    // 看redis中是否存在token
//    String tokenKey = "token_" + token;
//    String redisToken = redisService.get(tokenKey);
//    // 如果说redis中没有token(用户做过退出登录),则证明token已经失效
//    if (StringUtils.isEmpty(redisToken)) {
//      throw new CustomerAuthorizationException("token过期,请重新登录!");
//    }
//    // 如果token不一致
//    if (!token.equals(redisToken)) {
//      throw new CustomerAuthorizationException("token验证失败!");
//    }
//
//    // 解析token
//    String username = jwtUtils.getUsernameFromToken(token);
//    if (StringUtils.isEmpty(username)) {
//      throw new CustomerAuthorizationException("token解析失败!");
//    }
//    // 获取用户信息
//    UserDetails user = customerUserDetailsService.loadUserByUsername(username);
//    if (user == null) {
//      throw new CustomerAuthorizationException("token验证失败!");
//    }
//    UsernamePasswordAuthenticationToken authenticationToken =
//        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//    // 设置到spring security上下文
//    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//  }
//  // 验证码验证
//  private void validateImage(HttpServletRequest request) {
//    // 1.获取验证码
//    String code = request.getParameter("code");
//    // 从redis中获取验证码
//    // 2.获取请求的ip
//    String ip = request.getRemoteAddr();
//    String key = ip + code;
//    // 3.获取redis中的验证码
//    String redisCode = redisService.get(key);
//    if (StringUtils.isEmpty(code)) {
//      throw new ImageException("验证码不能为空!");
//    }
//    if (!code.equalsIgnoreCase(redisCode)) {
//      throw new ImageException("验证码错误!");
//    }
//  }
}
