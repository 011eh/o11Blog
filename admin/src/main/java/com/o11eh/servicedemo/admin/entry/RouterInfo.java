package com.o11eh.servicedemo.admin.entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.prefs.PreferencesFactory;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterInfo {
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class Meta {
        private String title;
        private String icon;
        private Boolean noCache;
        private Boolean breadcrumb;
        private Boolean affix;
    }

    private String path;
    private String component;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Meta meta;
}
