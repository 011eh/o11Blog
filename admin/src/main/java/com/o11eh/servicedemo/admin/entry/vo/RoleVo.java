package com.o11eh.servicedemo.admin.entry.vo;

import com.o11eh.servicedemo.admin.enums.Status;
import com.o11eh.servicedemo.validation.RefId;
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

    @RefId("back_permission")
    private List<String> permissionIds;

}
