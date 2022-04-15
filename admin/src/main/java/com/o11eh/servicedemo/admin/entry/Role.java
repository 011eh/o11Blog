package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.o11eh.servicedemo.admin.enums.Status;
import com.o11eh.servicedemo.validation.RefId;
import io.swagger.annotations.ApiModel;
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
@TableName("back_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Role对象", description = "角色")
public class Role extends BaseEntry {

    private static final long serialVersionUID = 1L;

    private String name;
    private String summary;
    private Status status;


    @TableField(exist = false)
    @RefId("back_permission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> permissionIds;
}
