package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.regex.PatternSyntaxException;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@TableName("back_permission")
@Data
public class Permission extends BaseEntry<Permission> {
    private String name;
    private String permissionKey;
}
