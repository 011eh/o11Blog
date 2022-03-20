package com.o11eh.servicedemo.admin.config;

import com.o11eh.servicedemo.admin.security.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    public static final int HASH_ITERATIONS = 5;

    public static final String LOGIN_URL = "/auth/toLogin";
    public static final String UNAUTHORIZED_URL = "/auth/unauthorized";
    public static final int MINUTE = 60;
    public static final int Day = 24 * 60 * MINUTE;
    public static final String CIPHER_KEY = "SlNMMTBBYkNmbEVWTEFWMw==";


    @Bean
    public SimpleCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        matcher.setHashIterations(HASH_ITERATIONS);
        return matcher;
    }

    @Bean
    public UserRealm userRealm(SimpleCredentialsMatcher matcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        SimpleCookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setMaxAge(30 * MINUTE);

        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionIdCookie(cookie);
        manager.setSessionIdCookieEnabled(true);
        return manager;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        SimpleCookie cookie = new SimpleCookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
        cookie.setMaxAge(5 * Day);

        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie);
        cookieRememberMeManager.setCipherKey(Base64.decode(CIPHER_KEY));
        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm realm, DefaultWebSessionManager sessionManager,
                                                     CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setUnauthorizedUrl(UNAUTHORIZED_URL);
        factoryBean.setLoginUrl(LOGIN_URL);
        factoryBean.setSecurityManager(securityManager);

        factoryBean.setFilterChainDefinitionMap(new LinkedHashMap<String, String>() {{
            put("/swagger-ui/**", "anon");
            put("/swagger-resources/**", "anon");
            put("/v3/api-docs", "anon");

            put("/auth/login", "anon");
            put("/auth/toLogin", "anon");
            put("/auth/info", "anon");
            put("/auth/unauthorized", "anon");

            put("/admin/*", "perms[admin:query]");
            put("/admin/page", "perms[admin:query]");
            put("/admin/add", "perms[admin:add]");
            put("/admin/update", "perms[admin:update]");
            put("/admin/delete", "perms[admin:delete]");

            put("/permission/*", "perms[permission:query]");
            put("/permission/page", "perms[permission:query]");
            put("/permission/add", "perms[permission:add]");
            put("/permission/update", "perms[permission:update]");
            put("/permission/delete", "perms[permission:delete]");

            put("/role/*", "perms[role:query]");
            put("/role/page", "perms[role:query]");
            put("/role/add", "perms[role:add]");
            put("/role/update", "perms[role:update]");
            put("/role/delete", "perms[role:delete]");

            put("/**", "authc");
        }});
        return factoryBean;
    }
}