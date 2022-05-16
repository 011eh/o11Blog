package com.o11eh.servicedemo.admin.entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterInfo {

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
