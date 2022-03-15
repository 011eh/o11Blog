package com.o11eh.servicedemo.base.utils;

import com.o11eh.servicedemo.base.constants.BaseConstants;
import com.o11eh.servicedemo.base.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * @author 011eh
 * @since 2022/02/20 17:26
 */
public class JwtUtil {
    public static final String signature = "HVBnvSv8jt-3-rKdi!FD8M28_7:YAa";
    public static final String TYPE = "typ";
    public static final String JWT = "JWT";
    public static final String HEADER = "Authorization";


    public static String createToken(String username, String audience, String issuer, long ttl) {
        long nowMills = System.currentTimeMillis();
        Date nowDate = new Date(nowMills);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(TYPE, JWT)
                .claim(BaseConstants.CREATE_TIME, nowDate)
                .setSubject(username)
                .setIssuer(issuer)
                .setAudience(audience)
                .setNotBefore(nowDate)
                .signWith(SignatureAlgorithm.HS256, signature);
        if (ttl >= 0) {
            builder.setExpiration(new Date(nowMills + ttl));
        }
        return builder.compact();
    }

    public static String getToken(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getHeader(HEADER);
    }

    public static Claims parseJWT(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(signature))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.e();
        }
    }

    public static boolean isExpired(String token) {
        return parseJWT(token).getExpiration().before(new Date());
    }

    public static String getUsername(String token) {
        return parseJWT(token).getSubject();
    }
}
