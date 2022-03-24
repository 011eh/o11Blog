package com.o11eh.servicedemo.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.o11eh.servicedemo.admin.config.AuthInfo;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.config.BusinessException;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.service.PermissionService;
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
import java.util.List;

@RestController
@RequestMapping("auth")
@Api(tags = "登录")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestParam String username, @Valid @RequestParam String password, boolean rememberMe) {

        Admin admin = adminService.getByUsername(username);

        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername());
        String pwd = new SimpleHash(Md5Hash.ALGORITHM_NAME, password, credentialsSalt,
                5).toString();

        if (!StrUtil.equals(admin.getPassword(), pwd)) {
            throw new IncorrectCredentialsException();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        SecurityUtils.getSubject().login(token);

        return Result.success(admin.getId());
    }


    @ApiOperation("用户信息")
    @GetMapping("info")
    public Result getUserInfo() {
        List<Permission> permissionByRoleId = permissionService.getPermissionByRoleId(1L);
        return Result.success(permissionByRoleId);
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
