package com.o11eh.servicedemo.admin.controller;


import com.o11eh.servicedemo.admin.constants.ApiConst;
import com.o11eh.servicedemo.commons.entry.Admin;
import com.o11eh.servicedemo.commons.service.AdminService;
import com.o11eh.servicedemo.servicebase.constants.BaseApiConst;
import com.o11eh.servicedemo.servicebase.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@RestController
@RequestMapping(ApiConst.ADMIN)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(BaseApiConst.ADD)
    public Result<?> create() {
        Admin admin = new Admin();
        admin.setUsername("32");
        admin.setPassword("3232");
        admin.insert();
        Admin get = adminService.getById(admin.getId());
        return Result.success(get);
    }
}
