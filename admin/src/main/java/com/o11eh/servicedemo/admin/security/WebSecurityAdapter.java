package com.o11eh.servicedemo.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 011eh
 * @since 2022/02/21 20:33
 */
@Configuration
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //因为SpringSecurity使用X-Frame-Options防止网页被Frame。所以需要关闭为了让后端的接口管理的swagger页面正常显示
        http.headers().frameOptions().disable();

        http
                //新加入,允许跨域
                .cors()
                .and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 异常的处理器，将执行未鉴权的处理方法
                .exceptionHandling()
//                .authenticationEntryPoint()
                .and()
                // 基于token，所以不需要session【但禁用后，将导致UserDetailsService每次都会执行，无法缓存用户信息】
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/*",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/actuator/**",
                        "/druid/**"
                ).permitAll()
                // 对于获取token的RestApi要允许匿名访问
                .antMatchers("/auth/**",
                        "/creatCode/**",
                        "/file/**"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 添加两个过滤器
        // JwtAuthenticationTokenFilter: JWT认证过滤器,验证token有效性
        // UsernamePasswordAuthenticationFilter: 认证操作全靠这个过滤器
//        http.addFilterBefore(registrationBean(new JwtAuthenticationTokenFilter()).getFilter(),
//                UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();
    }
}
