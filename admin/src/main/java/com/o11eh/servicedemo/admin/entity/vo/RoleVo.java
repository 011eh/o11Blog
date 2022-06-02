package com.o11eh.servicedemo.admin.entity.vo;

import com.o11eh.servicedemo.servicebase.enums.Status;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Data
public class RoleVo {

    private String name;
    private String summary;
    private Status status;
    private List<String> permissionKeys;

}
