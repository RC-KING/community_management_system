package com.jdd.community_management_system.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.utils.dataUtils.LoginResult;
import com.jdd.community_management_system.utils.jwt.JwtUtils;
import com.jdd.community_management_system.utils.redis.RedisService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //1.生成token
        SysUser user = (SysUser)authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        //该token过期的时间，返回给前端
        long expireTime = Jwts.parser() //得到DefaultJwtParser
                .setSigningKey(jwtUtils.getSecret()) //设置签名的秘钥
                .parseClaimsJws(token.replace("jwt_", ""))
                .getBody()
                .getExpiration()
                .getTime();
        LoginResult loginResult = new LoginResult();
        loginResult.setId(user.getId());
        loginResult.setCode(200);
        loginResult.setToken(token);
        loginResult.setExpireTime(expireTime);
        // 将loginResult作为响应参数返回
        String res = JSONObject.toJSONString(loginResult,
                SerializerFeature.DisableCircularReferenceDetect);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        // 登录成功之后将SysUser实体作为响应数据返回
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();

        // 把生成的token存入redis
        String tokenKey = "token_" + token;
        redisService.set(tokenKey, token, jwtUtils.getExpiration() / 1000);

        //清空验证码
        String inputCode = request.getParameter("code");
        String key = request.getRemoteAddr()+inputCode;
        redisService.del(key);




    }
}
