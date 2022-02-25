package com.o11eh.servicedemo.admin.controller;


import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.base.constants.BaseApiConstants;
import com.o11eh.servicedemo.base.controller.BaseController;
import com.o11eh.servicedemo.base.resp.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    RoleService roleService;

    @GetMapping("/hello")
    public Result hello() {
        return Result.success();
    }

    @PostMapping(BaseApiConstants.ADD)
    public Result addRole(@RequestBody Role role) {
        Long id = roleService.add(role);
        return Result.success(id);
    }
}
