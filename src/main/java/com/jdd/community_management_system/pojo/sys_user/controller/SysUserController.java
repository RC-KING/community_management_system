package com.jdd.community_management_system.pojo.sys_user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.jdd.community_management_system.config.security.exception.CustomerAuthenticationException;
import com.jdd.community_management_system.pojo.department.entity.Department;
import com.jdd.community_management_system.pojo.department.service.DepartmentService;
import com.jdd.community_management_system.pojo.sys_user.entity.SysUser;
import com.jdd.community_management_system.pojo.sys_user.service.impl.SysUserServiceImpl;
import com.jdd.community_management_system.utils.dataUtils.ResultUtils;
import com.jdd.community_management_system.utils.dataUtils.ResultVo;
import com.jdd.community_management_system.utils.dataUtils.TokenVo;
import com.jdd.community_management_system.utils.jwt.JwtUtils;
import com.jdd.community_management_system.utils.log.annotation.SysLog;
import com.jdd.community_management_system.utils.redis.RedisService;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * 用户 前端控制器
 *
 * @author 金大大
 * @since 2022-01-25
 */
@RestController
@RequestMapping("/api/sys_user")
public class SysUserController {
  @Autowired SysUserServiceImpl sysUserService;

  @SysLog("新增用户")
  @PostMapping
  @ApiOperation("新增用户")
  public ResultVo addSysUser(@RequestBody SysUser user) {
    if (sysUserService.save(user)) {
      return ResultUtils.success("新增用户成功!", user);
    } else {
      return ResultUtils.error("新增用户失败!", user);
    }
  }

  // PathVariable传参的时候要注意前端传参的配置
  @SysLog("删除单个删除用户")
  @DeleteMapping("/{id}")
  @ApiOperation("根据ID,删除单个删除用户")
  public ResultVo delSysUser(@PathVariable Long id) {
    if (sysUserService.removeById(id)) {
      return ResultUtils.success("删除用户成功!", id);
    } else {
      return ResultUtils.error("删除用户失败!", id);
    }
  }

  @SysLog("修改用户")
  @PatchMapping()
  @ApiOperation("修改用户")
  public ResultVo updateSysUser(@RequestBody SysUser user) {
    // 更新前查看用户名是否在数据库中存在
    SysUser userByUsername = sysUserService.getUserByUsername(user.getUsername());
    if(userByUsername!=null){
      //如果在数据中查得到这个用户,有两种可能:
      //    一是用户更改了名字,和其他用户同名
      //    二是用户没有更新名字查到的是自己,这个时候就需要user的ID来判断了
      if(user.getId()!=userByUsername.getId()){
        // 两者ID相等表示是同一个用户,表示可以进行继续的更新操作
        // 否则表示用户修改了用户名,并且和其他用户冲突了
        return ResultUtils.error("请使用其他用户名,更新用户失败!", user);
      }
    }
    // 查询乐观锁,首先得查询出来Version字段
    SysUser DBGetUser = sysUserService.getById(user.getId());
    // 设置Version字段
    user.setVersion(DBGetUser.getVersion());
    // 采取更新措施
    if (sysUserService.updateById(user)) {
      return ResultUtils.success("更新用户成功!", user);
    } else {
      return ResultUtils.error("更新用户失败!", user);
    }
  }

  @SysLog("查询所有用户")
  @GetMapping
  @ApiOperation("查询所有用户")
  public ResultVo getAllSysUser() {
    List<SysUser> list = sysUserService.list();
    if (list.size() != 0) {
      return ResultUtils.success("获取所有用户成功!", list);
    } else {
      return ResultUtils.error("获取所有用户失败!", list);
    }
  }
  //////////////////////////////////////////////////////////////////////////////////

  @Autowired PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  @ApiOperation("用户注册")
  public ResultVo sysUserRegister(@RequestBody SysUser user) {
    // 1.判断用户名是否重复
    SysUser user1 = sysUserService.getUserByUsername(user.getUsername());
    if (user1 != null) {
      return ResultUtils.error("已存在该用户,请使用其他用户名注册!");
    }
    // 2.密码加密
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    if (sysUserService.save(user)) {
      return ResultUtils.success("注册成功!", user);
    } else {
      return ResultUtils.error("注册失败!", user);
    }
  }

  @Autowired private RedisService redisService;
  @Autowired private RedisTemplate<String, Object> redisTemplate;

  // Redis测试
  @GetMapping("/test_redis")
  public ResultVo getAllUser() {
    redisService.set("name", "张三", 60L);
    String name = redisService.get("name");
    List<SysUser> list = sysUserService.list();
    return new ResultVo("成功", 200, name);
  }

  @Autowired private DefaultKaptcha defaultKaptcha;

  @ApiOperation("生成验证码")
  @PostMapping("/image")
  public void imageCode(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // 设置页面缓存方式(不缓存,不存储)
    response.setHeader("Cache-Control", "no-store, no-cache");
    // 设置以图片的形式响应
    response.setContentType("image/jpeg");
    // 1. 获取验证码工具生成的字符
    String yzm = defaultKaptcha.createText();
    // 2. 获取ip(ip是redis中存储验证码的文件目录)
    String ip = request.getRemoteAddr();
    // 3. 将验证码字符存入redis中
    //                  redis的key       设置验证码30S过期
    redisService.set(ip + yzm, yzm, 60L);
    // 4.生成验证码图片
    BufferedImage image = defaultKaptcha.createImage(yzm);
    // 5.输出给前端
    ServletOutputStream out = response.getOutputStream();
    ImageIO.write(image, "jpg", out);
    if (out != null) {
      out.close();
    }
  }

  @SysLog("退出登录")
  @ApiOperation("退出登录")
  @PostMapping("/login_out")
  public ResultVo loginOut(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    // 获取用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // 如果存在用户信息
    if (authentication != null) {
      // 调用SpringSecurity的自带的退出登录
      new SecurityContextLogoutHandler().logout(request, response, authentication);
      // 从头部获取token
      String token = request.getHeader("token");
      // 从参数体获取token
      if (StringUtils.isEmpty(token)) {
        token = request.getParameter("token");
      }
      if (StringUtils.isNotEmpty(token)) {
        // 清空token
        redisService.del("token_" + token);
      } else {
        throw new AuthenticationException("用户token异常!");
      }
    }
    return ResultUtils.success("退出成功!");
  }

  @Autowired JwtUtils jwtUtils;

  @SysLog("刷新token")
  @ApiOperation(value = "刷新token", notes = "刷新token")
  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token")})
  @PostMapping(value = "/refreshToken")
  public ResultVo refreshToken(HttpServletRequest request) {
    // 获取前端传来的token 首先从header里面取，如果没有，从参数里面取
    String token = request.getHeader("token");
    if (StringUtils.isEmpty(token)) {
      token = request.getParameter("token");
    }
    if (StringUtils.isBlank(token)) {
      throw new CustomerAuthenticationException("token不能为空!");
    }
    String reToken = "";

    UserDetails details =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // 验证原来的token是否合法
    if (jwtUtils.validateToken(token, details)) {
      // 生成新的token
      reToken = jwtUtils.refreshToken(token);
    }
    // 取出本次token到期的时间,返回前端做判断
    long expireTime =
        Jwts.parser() // 得到DefaultJwtParser
            .setSigningKey(jwtUtils.getSecret()) // 设置签名的秘钥
            .parseClaimsJws(reToken.replace("jwt_", ""))
            .getBody()
            .getExpiration()
            .getTime();
    // rides中清除原来的token
    String oldTokenKey = "token_" + token;
    redisService.del(oldTokenKey);
    // rides中存储新的token
    String newTokenKey = "token_" + reToken;
    redisService.set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000);
    TokenVo vo = new TokenVo();
    vo.setToken(reToken);
    vo.setExpireTime(expireTime);
    return ResultUtils.success("生成token成功!", vo);
  }


  @Autowired
  DepartmentService departmentService;


  // 这样的路径传参格式,前端传入参数的时候需要注意,不能使用普通的get请求,会报错
  @SysLog(value = "根据部门Id获取用户列表")
  @ApiOperation(value = "根据部门Id获取用户列表")
  @ApiImplicitParams({@ApiImplicitParam(name = "deptId",required = true,value = "部门Id")})
  @GetMapping("/list/{deptId}/{currentPage}/{pageSize}")
  public ResultVo getTablesList(@PathVariable(value = "deptId") Long deptId,
                                @PathVariable(value = "currentPage") Long currentPage,
                                @PathVariable(value = "pageSize") Long pageSize){
    IPage<SysUser> tablesList = sysUserService.getUserList(currentPage,pageSize,deptId);
    System.out.println(tablesList.getTotal());
    List<SysUser> records = tablesList.getRecords();
    // 冗余字段的填充
    for (SysUser record : records) {
      Department department = departmentService.getBaseMapper().selectById(record.getDeptId());
      record.setDeptName(department.getName());
      //TODO:等这几个创建出来了就可以完善了
      // record.getClassId();
      // record.getClubId();
      // record.getCollegeId();
      // record.getMajorId();
    }

    return ResultUtils.success("查询成功!",tablesList);
  }


}
