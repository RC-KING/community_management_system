package com.jdd.community_management_system.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdd.community_management_system.utils.result_data.ResultVo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * 匿名用户访问资源时处理器
 */
@Component("customAuthenticationHandler")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e)
      throws IOException, ServletException {
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    ServletOutputStream out = httpServletResponse.getOutputStream();
    String res =
        JSONObject.toJSONString(
            new ResultVo<>("匿名用户无权限访问！", 600, null),
            SerializerFeature.DisableCircularReferenceDetect);
    out.write(res.getBytes(StandardCharsets.UTF_8));
    out.flush();
    out.close();
  }
}
