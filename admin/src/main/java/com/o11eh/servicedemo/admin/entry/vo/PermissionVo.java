package com.o11eh.servicedemo.admin.entry.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PermissionVo {
    private String name;
    private String permissionKey;
    private Status status;
    private String parentId;
    private Integer sort;
    private ResourceType resourceType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> routerInfo = new HashMap<>();

    @TableField(exist = false)
    private List<Permission> children;

    @JsonAnyGetter
    public Map<String, Object> getRouterInfo() {
        return routerInfo;
    }

    @JsonAnySetter
    public void add(String key, Object value) {
        routerInfo.put(key, value);
    }
}
