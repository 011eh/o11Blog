package com.o11eh.o11blog.servicebase.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpUtil {
    private final static List<String> IP_HEADERS = Stream.of(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    ).collect(Collectors.toList());

    public static String getIPAddress(HttpServletRequest request) {

        for (String header : IP_HEADERS) {
            String ipListString = request.getHeader(header);
            if (StrUtil.isNotBlank(ipListString) && !"unknown".equalsIgnoreCase(ipListString)) {
                return ipListString.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }

    public static String getIPAddress() {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        for (String header : IP_HEADERS) {
            String ipListString = request.getHeader(header);
            if (StrUtil.isNotBlank(ipListString) && !"unknown".equalsIgnoreCase(ipListString)) {
                return ipListString.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }
}
