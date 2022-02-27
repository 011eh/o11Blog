package com.o11eh.servicedemo.base.utils;

import com.o11eh.servicedemo.base.constants.BaseConstants;
import com.o11eh.servicedemo.base.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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

    public static String createToken(String userName, String roleName,
                                     String audience, String issuer, long ttl) {
        long nowMills = System.currentTimeMillis();
        Date nowDate = new Date(nowMills);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(TYPE, JWT)
                .claim(BaseConstants.ROLE_NAME, roleName)
                .claim(BaseConstants.CREATE_TIME, nowDate)
                .setSubject(userName)
                .setIssuer(issuer)
                .setAudience(audience)
                .setNotBefore(nowDate)
                .signWith(SignatureAlgorithm.HS256, signature);
        if (ttl >= 0) {
            builder.setExpiration(new Date(nowMills + ttl));
        }
        return builder.compact();
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

    public static boolean verify(String token, String username) {
        String subject = getUsername(token);
        boolean success = subject.equals(username);
        return !isExpired(token) && success;
    }

    public static String getUsername(String token) {
        return parseJWT(token).getSubject();
    }
}
