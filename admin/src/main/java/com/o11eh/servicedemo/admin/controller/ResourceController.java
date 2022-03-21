package com.o11eh.servicedemo.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.o11eh.servicedemo.base.utils.jackson.JsonUtl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resource")
public class ResourceController {
    @GetMapping("menu")
    public JsonNode menu() throws JsonProcessingException {
        String menu = "[\n" +
                "  {\n" +
                "    \"path\": \"/permission\",\n" +
                "    \"component\": \"layout\",\n" +
                "    \"redirect\": \"/permission/page\",\n" +
                "    \"alwaysShow\": true,\n" +
                "    \"name\": \"权限1\",\n" +
                "    \"meta\": {\n" +
                "      \"title\": \"权限2\",\n" +
                "      \"icon\": \"lock\",\n" +
                "      \"roles\": [\n" +
                "        \"role:list\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"path\": \"page\",\n" +
                "        \"component\": \"views/permission/page\",\n" +
                "        \"name\": \"PagePermission\",\n" +
                "        \"meta\": {\n" +
                "          \"title\": \"Page Permission\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"path\": \"directive\",\n" +
                "        \"component\": \"views/permission/directive\",\n" +
                "        \"name\": \"DirectivePermission\",\n" +
                "        \"meta\": {\n" +
                "          \"title\": \"Directive Permission\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"path\": \"role\",\n" +
                "        \"component\": \"Role\",\n" +
                "        \"name\": \"RolePermission\",\n" +
                "        \"meta\": {\n" +
                "          \"title\": \"Role Permission\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]\n";
        JsonNode jsonNode = JsonUtl.OBJECT_MAPPER.readTree(menu);
        return jsonNode;
    }
}
