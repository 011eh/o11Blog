package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.o11eh.servicedemo.admin.config.validation.ColumnsUnique;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.servicebase.enums.ResourceType;
import com.o11eh.servicedemo.servicebase.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@Data
@TableName(value = "back_permission", autoResultMap = true)
@ColumnsUnique(tableName = "back_permission", properties = "name")
public class Permission extends BaseEntry {

    @NotEmpty
    private String name;

    @NotEmpty
    private String permissionKey;

    @NotNull
    private ResourceType resourceType;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @RefId("back_permission")
    private String parentId;
    private Integer sort;
    private Status status;

    @TableField(exist = false)
    private List<Permission> children;

    @JsonUnwrapped
    @TableField(typeHandler = JacksonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private RouterInfo routerInfo;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RouterInfo {

        private String id;
        private String name;
        private String parentId;
        private String path;
        private String component;
        private String redirect;
        private Boolean hidden;
        private Boolean alwaysShow;
        private Meta meta;
        private List<RouterInfo> children;

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        static class Meta {
            private String title;
            private String icon;
            private Boolean noCache;
            private Boolean breadcrumb;
            private Boolean affix;
        }
    }

    @Data
    public static class ApiRef {
        private String api;
        private String httpMethod;
        private boolean match;
    }
}
