package com.o11eh.servicedemo.admin.security;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 011eh
 * @since 2022/02/21 19:58
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(AUTH_HEADER);

        if (ObjectUtil.isNotNull(authToken)) {
            log.info("Token为：" + authToken);
        }
    }
}
