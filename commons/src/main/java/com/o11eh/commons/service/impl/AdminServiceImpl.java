package com.o11eh.commons.service.impl;

import com.o11eh.commons.entry.Admin;
import com.o11eh.commons.mapper.AdminMapper;
import com.o11eh.commons.service.AdminService;
import com.o11eh.servicedemo.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, Admin> implements AdminService {

}
