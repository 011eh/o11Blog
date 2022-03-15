package com.o11eh.servicedemo.admin.security;

import com.o11eh.servicedemo.base.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if ("/auth/login".equals(((HttpServletRequest) request).getRequestURI())) {
            return true;
        }
        try {
            executeLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = JwtUtil.getToken(request);

        try {
            getSubject(request, response).login(new BearerToken(token));
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return false;
    }
}
