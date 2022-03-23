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
        String menu = "[  {\n" +
                "    \"path\": \"/permission\",\n" +
                "    \"component\": \"layout\",\n" +
                "    \"name\": \"permission\",\n" +
                "    \"hidden\": false,\n" +
                "    \"redirect\": \"/permission/page\",\n" +
                "    \"alwaysShow\": false,\n" +
                "    \"meta\": {\n" +
                "      \"title\": \"资源权限\",\n" +
                "      \"icon\": \"lock\",\n" +
                "      \"roles\": [\n" +
                "        \"role:list\"\n" +
                "      ],\n" +
                "      \"noCache\": true,\n" +
                "      \"affix\": false,\n" +
                "      \"breadcrumb\": true\n" +
                "    },\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"path\": \"page\",\n" +
                "        \"component\": \"page\",\n" +
                "        \"name\": \"user\",\n" +
                "        \"meta\": {\n" +
                "          \"title\": \"用户管理\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"path\": \"directive\",\n" +
                "        \"hidden\": false,\n" +
                "        \"affix\": true,\n" +
                "        \"component\": \"directive\",\n" +
                "        \"name\": \"role\",\n" +
                "        \"meta\": {\n" +
                "          \"title\": \"角色管理\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"path\": \"role\",\n" +
                "        \"component\": \"role\",\n" +
                "        \"name\": \"menu\",\n" +
                "        \"meta\": {\n" +
                "          \"title\": \"菜单管理\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n]";

        JsonNode node = JsonUtl.OBJECT_MAPPER.readTree(menu);
        return Result.success(node);
    }
}
