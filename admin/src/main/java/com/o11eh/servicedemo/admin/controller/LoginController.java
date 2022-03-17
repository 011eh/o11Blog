package com.o11eh.servicedemo.admin.controller;

import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.base.exception.BusinessException;
import com.o11eh.servicedemo.base.resp.Result;
import com.o11eh.servicedemo.servicebase.constants.RedisCo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestParam String username, @Valid @RequestParam String password, boolean rememberMe) {

        Admin admin = adminService.getByUsername(username);

        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername());
        String pwd = new SimpleHash(Md5Hash.ALGORITHM_NAME, password, credentialsSalt,
                5).toString();

        if (!admin.getPassword().equals(pwd)) {
            throw new IncorrectCredentialsException();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        SecurityUtils.getSubject().login(token);

        redisTemplate.opsForValue().set(RedisCo.ADMIN + RedisCo.COLON + admin.getId(), admin);
        return Result.success();
    }


    @GetMapping("info")
    public Result getUserInfo(long userId) {
        Admin admin = (Admin) redisTemplate.opsForValue().get(RedisCo.ADMIN + RedisCo.COLON + userId);
        return Result.success(admin);
    }

    @GetMapping("logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    @GetMapping("toLogin")
    public Result toLogin() {
        throw BusinessException.e("您尚未登录");
    }

    @GetMapping("unauthorized")
    public Result unauthorized() {
        throw BusinessException.e("无相关权限进行操作");
    }
}
