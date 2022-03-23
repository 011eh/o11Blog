package com.o11eh.servicedemo.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.o11eh.servicedemo.base.resp.Result;
import com.o11eh.servicedemo.base.utils.jackson.JsonUtl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resource")
public class ResourceController {
    @GetMapping("menu")
    public Result menu() throws JsonProcessingException {
        String menu = "[\n" +
                "    {\n" +
                "      \"path\": \"/permission\",\n" +
                "      \"component\": \"layout\",\n" +
                "      \"redirect\": \"/permission/role\",\n" +
                "      \"alwaysShow\": true,\n" +
                "      \"name\": \"permission\",\n" +
                "      \"meta\": {\n" +
                "        \"title\": \"权限2\",\n" +
                "        \"icon\": \"lock\",\n" +
                "        \"roles\": [\n" +
                "          \"role:list\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"path\": \"/page\",\n" +
                "          \"component\": \"page\",\n" +
                "          \"name\": \"PagePermission\",\n" +
                "          \"meta\": {\n" +
                "            \"icon\": \"lock\",\n" +
                "            \"title\": \"Page Permission\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"directive\",\n" +
                "          \"component\": \"directive\",\n" +
                "          \"name\": \"DirectivePermission\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"Directive Permission\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"role\",\n" +
                "          \"component\": \"role\",\n" +
                "          \"name\": \"RolePermission\",\n" +
                "          \"meta\": {\n" +
                "            \"title\": \"Role Permission\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n";

        JsonNode node = JsonUtl.OBJECT_MAPPER.readTree(menu);
        return Result.success(node);
    }
}
