package com.o11eh.servicedemo.admin.security;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShiroUtils {
    public static final String AUTHENTICATION = "authentication";

    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String NORMAL_ADMIN = "NORMAL_ADMIN";


    public static Map<String, String> filterChainDefinitionMap() {
        return new HashMap<String, String>() {{
            put("/admin/authc", "authc");
            put("/admin/normal", "hasAnyRoles[SUPER_ADMIN,NORMAL_ADMIN]");
            put("/admin/admin", "roles[SUPER_ADMIN],perms[add]");
        }};
    }
}
