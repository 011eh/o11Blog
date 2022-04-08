package com.o11eh.servicedemo.admin.entry.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o11eh.servicedemo.admin.entry.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionTreeVo {
    private String id;
    private String name;
    private List<PermissionTreeVo> children;

    public PermissionTreeVo(Permission permission) {
        id = permission.getId();
        name = permission.getName();
    }

    public void setChildren(List<Permission> children) {
        if (children == null) {
            return;
        }
        List<PermissionTreeVo> treeChildren = children.stream().map(permission -> {
            PermissionTreeVo treeVo = new PermissionTreeVo();
            treeVo.id = permission.getId();
            treeVo.name = permission.getName();
            return treeVo;
        }).collect(Collectors.toList());
        this.children = treeChildren;
    }
}
