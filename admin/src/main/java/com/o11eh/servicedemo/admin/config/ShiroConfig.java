package com.o11eh.servicedemo.admin.config;

import com.o11eh.servicedemo.admin.security.RoleOrFilter;
import com.o11eh.servicedemo.admin.security.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
        factoryBean.setUnauthorizedUrl(UNAUTHORIZED_URL);
        factoryBean.setLoginUrl(LOGIN_URL);
        factoryBean.setSecurityManager(securityManager);

        factoryBean.setFilters(new LinkedHashMap<String, Filter>() {{
            put("hasAnyRoles", new RoleOrFilter());
        }});
        factoryBean.setFilterChainDefinitionMap(new HashMap<String, String>() {{
            put("/admin/authc", "authc");
            put("/admin/perm1", "perms[admin:perm1]");
            put("/admin/perm2", "perms[admin:perm2]");
            put("/admin/perm3", "perms[admin:perm3]");
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