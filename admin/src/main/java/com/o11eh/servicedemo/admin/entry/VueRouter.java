package com.o11eh.servicedemo.admin.entry;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class VueRouter {

    @JsonAnySetter
    private Map<String, Object> otherInfo = new HashMap<>();

    private List<VueRouter> children;

    @JsonAnyGetter
    public Map<String, Object> getOtherInfo() {
        return otherInfo;
    }
}
