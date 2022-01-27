package com.jdd.community_management_system.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** 认证用户访问无权限资源时处理器 */
@Component("myCustomAccessDenyHandler")
public class MyCustomAccessDenyHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AccessDeniedException e)
      throws IOException, ServletException {
    // 响应消息是JSON格式的
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    ServletOutputStream out = httpServletResponse.getOutputStream();
    String res =
        JSONObject.toJSONString(
            new ResultVo<>("无权限访问,请联系管理员！", 700, null),
            SerializerFeature.DisableCircularReferenceDetect);
    out.write(res.getBytes(StandardCharsets.UTF_8));
    out.flush();
    out.close();
  }
}
