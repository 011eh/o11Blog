package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission extends BaseEntry<Permission> {
    private String name;
    private String permissionKey;
    private Integer status;
    private Long parentId;
    private Integer sort;
    private ResourceType resourceType;

    @JsonAnySetter
    private Map<String, Object> routerInfo = resourceType == ResourceType.MENU ? new HashMap<>() : null;

    @TableField(exist = false)
    private List<Permission> children;

    @JsonAnyGetter
    public Map<String, Object> getRouterInfo() {
        return routerInfo;
    }
}
