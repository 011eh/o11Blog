package com.o11eh.servicedemo.admin.controller;


import com.o11eh.servicedemo.base.controller.BaseController;
import com.o11eh.servicedemo.base.resp.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/role")
@RequiresRoles("SUPER_ADMIN")
public class RoleController extends BaseController {
    @GetMapping("/hello")
    public Result hello() {
        return Result.success();
    }
}
