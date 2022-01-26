package com.jdd.community_management_system.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功之后的处理器
 */
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取用户主体
        SysUser SysUser = (SysUser)authentication.getPrincipal();
        String res = JSONObject.toJSONString(SysUser,
                SerializerFeature.DisableCircularReferenceDetect);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        // 登录成功之后将SysUser实体作为响应数据返回
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();

    }
}
