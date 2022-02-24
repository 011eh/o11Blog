package com.o11eh.servicedemo.admin.config;

import com.o11eh.servicedemo.admin.security.UserRealm;
import com.o11eh.servicedemo.admin.service.AdminService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfig {

    public static final int HASH_ITERATIONS = 5;
    public static final String LOGIN_URL = "/admin/toLogin";
    public static final String UNAUTHORIZED_URL = "/admin/unauthorized";

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
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setFilterChainDefinitionMap(new HashMap<String, String>() {{
            put("/admin/authc", "authc");
            put("/admin/normal", "roles[normalAdmin]");
            put("/admin/admin", "roles[admin],perms[add]");
        }});
        factoryBean.setUnauthorizedUrl(UNAUTHORIZED_URL);
        factoryBean.setLoginUrl(LOGIN_URL);
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