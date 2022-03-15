package com.o11eh.servicedemo.admin.config;

import com.o11eh.servicedemo.admin.security.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfig {

    public static final int HASH_ITERATIONS = 5;
    public static final String LOGIN_URL = "/auth/toLogin";
    public static final String UNAUTHORIZED_URL = "/auth/unauthorized";

    @Bean
    public UserRealm userRealm(SimpleCredentialsMatcher matcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setUnauthorizedUrl(UNAUTHORIZED_URL);
        factoryBean.setLoginUrl(LOGIN_URL);
        factoryBean.setSecurityManager(securityManager);

        factoryBean.setFilterChainDefinitionMap(new HashMap<String, String>() {{
            put("/admin/*", "perms[admin:query]");
            put("/admin/page", "perms[admin:query]");
            put("/admin/add", "perms[admin:add]");
            put("/admin/update", "perms[admin:update]");
            put("/admin/delete", "perms[admin:delete]");

            put("/permission/", "perms[permission:query]");
            put("/permission/page", "perms[admin:query]");
            put("/permission/add", "perms[permission:add]");
            put("/permission/update", "perms[permission:update]");
            put("/permission/delete", "perms[permission:delete]");

            put("/role/*", "perms[role:query]");
            put("/role/page", "perms[role:query]");
            put("/role/add", "perms[role:add]");
            put("/role/update", "perms[role:update]");
            put("/role/delete", "perms[role:delete]");
        }});
        return factoryBean;
    }

    @Bean
    public SimpleCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        matcher.setHashIterations(HASH_ITERATIONS);
        return matcher;
    }
}