package com.o11eh.o11blog.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.servicebase.validation.StringId;
import com.o11eh.o11blog.admin.entity.ApiMatcher;
import com.o11eh.o11blog.admin.entity.Permission;
import com.o11eh.o11blog.admin.entity.vo.ApiMatcherVo;
import com.o11eh.o11blog.admin.entity.vo.PermissionVo;
import com.o11eh.o11blog.admin.service.ApiMatcherService;
import com.o11eh.o11blog.admin.service.PermissionService;
import com.o11eh.o11blog.servicebase.config.log.Log;
import com.o11eh.o11blog.servicebase.constants.Constants;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 13:30
 */
@RestController
@Api(tags = "权限")
@RequestMapping("permission")
@AllArgsConstructor
public class PermissionController {

    private PermissionService permissionService;
    private ApiMatcherService apiMatcherService;

    @Log
    @GetMapping("list")
    @SaCheckPermission("permission:list")
    @ApiOperation("授权列表")
    public Result list() {
        List<Permission> permissions = permissionService.getPermissionList();
        return Result.success(permissions);
    }

    @Log
    @GetMapping(Constants.Api.PATH_ID)
    @ApiOperation(Constants.Doc.DETAIL)
    public Result detail(@Valid @StringId @PathVariable String id) {
        Permission permission = permissionService.detail(id);
        return Result.success(permission);
    }

    @Log("权限新建")
    @PostMapping
    @ApiOperation(Constants.Doc.ADD)
    public Result create(@Valid @RequestBody PermissionVo vo) {
        Permission permission = BeanUtil.copyProperties(vo, Permission.class);
        permissionService.save(permission);
        return Result.successShowMsg();
    }

    @Log("权限更新")
    @PutMapping
    @ApiOperation(Constants.Doc.UPDATE)
    public Result update(@Valid @RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.successShowMsg();
    }

    @Log("权限删除")
    @DeleteMapping
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result delete(@RequestBody List<Long> ids) {
        permissionService.removeBatchByIds(ids);
        return Result.successShowMsg();
    }

    @Log
    @GetMapping("granted/{roleId}")
    public Result getRolePermissions(@PathVariable String roleId) {
        List<Permission> permissionIds = permissionService.getPermissionGranted(roleId);
        return Result.success(permissionIds);
    }

    @Log
    @ApiOperation("接口鉴权分页")
    @PostMapping("/apiMatcher/page")
    public Result apiMatcherPage(@RequestBody PageReq req) {
        Page<ApiMatcher> page = apiMatcherService.page(new Page<>(req.getCurrent(), req.getSize()));
        return Result.success(page);
    }


    @Log("接口鉴权创建")
    @ApiOperation("接口鉴权创建")
    @PostMapping("/apiMatcher")
    public Result createApiMatcher(@Valid @RequestBody ApiMatcherVo matcherVo) {
        ApiMatcher matcher = BeanUtil.copyProperties(matcherVo, ApiMatcher.class);
        apiMatcherService.save(matcher);
        return Result.successShowMsg(matcher.getId());
    }

    @Log("接口鉴权更新")
    @ApiOperation("接口鉴权更新")
    @PutMapping("/apiMatcher")
    public Result updateApiMatcher(@Valid @RequestBody ApiMatcher matcher) {
        apiMatcherService.updateById(matcher);
        return Result.successShowMsg();
    }

    @Log("接口鉴权删除")
    @ApiOperation("接口鉴权删除")
    @DeleteMapping("/apiMatcher")
    public Result deleteApiMatcher(@RequestBody List<String> id) {
        apiMatcherService.removeBatchByIds(id);
        return Result.successShowMsg();
    }
}
