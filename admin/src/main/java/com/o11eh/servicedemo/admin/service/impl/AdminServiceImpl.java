package com.o11eh.servicedemo.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.config.BusinessException;
import com.o11eh.servicedemo.admin.entity.Admin;
import com.o11eh.servicedemo.admin.entity.BaseEntry;
import com.o11eh.servicedemo.servicebase.entity.PageReq;
import com.o11eh.servicedemo.admin.mapper.AdminMapper;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.SysParamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, Admin> implements AdminService {

    private AdminMapper adminMapper;
    private SysParamService sysParamService;


    @Override
    public Admin login(String username, String password) {
        String encrypt = SaSecureUtil.md5BySalt(password, username);
        Admin admin = adminMapper.selectToLogin(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getUsername, username).eq(Admin::getPassword, encrypt));
        return admin;
    }

    @Override
    public Page<Admin> page(PageReq param) {

        Wrapper<Admin> wrapper = StrUtil.isBlank(param.getKeyword()) ?
                Wrappers.emptyWrapper() : Wrappers.<Admin>lambdaQuery().like(Admin::getUsername, param.getKeyword())
                .or(queryWrapper -> queryWrapper.like(Admin::getNickName, param.getKeyword()));

        return adminMapper.selectPage(new Page<>(param.getCurrent(), param.getSize()), wrapper);
    }

    @Override
    public List<Admin> dtoList() {
        return this.getDtoList(Wrappers.<Admin>lambdaQuery().select(BaseEntry::getId, Admin::getUsername));
    }

    @Override
    public void resetPassword(String oldPassword, String newPassword) {
        String username = (String) StpUtil.getLoginId();
        String oldEncrypt = SaSecureUtil.md5BySalt(oldPassword, username);
        String encryptDB = this.getOne(Wrappers.<Admin>lambdaQuery()
                .select(Admin::getPassword)
                .eq(Admin::getUsername, username)).getPassword();

        if (!encryptDB.equals(oldEncrypt)) {
            throw BusinessException.e("旧密码错误");
        }
        String newEncrypt = SaSecureUtil.md5BySalt(newPassword, username);
        this.update(Wrappers.<Admin>update()
                .eq("username", username)
                .set("password", newEncrypt));
    }

    @Override
    public void resetToDefaultPassword(String userId) {
        String password = sysParamService.getValueByKey(SysParamService.ADMIN_DEFAULT_PASSWORD);
        this.update(Wrappers.<Admin>update().eq("id", userId)
                .set("password", SaSecureUtil.md5BySalt(password, userId)));
    }

    @Override
    public String create(Admin admin) {
        String password = sysParamService.getValueByKey(SysParamService.ADMIN_DEFAULT_PASSWORD);
        admin.setPassword(SaSecureUtil.md5BySalt(password, admin.getUsername()));
        this.save(admin);
        return admin.getId();
    }

    @Override
    public String update(Admin admin) {
        this.updateById(admin);
        return admin.getId();
    }
}
