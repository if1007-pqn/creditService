package com.service.credit.util;

import java.util.Calendar;
import java.util.Date;

import com.service.credit.exception.TokenInvalidException;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JWTUtil {

    @Value("${spring.secretKey}")
    private static String key = "oiqWe.jsnsSfxzcbvASOI!#A"; //TODO: server to load configs

    public static final String TOKEN_HEADER = "Authentication";

    // public static String create(String id, String owner) {
    //     Date d = new Date();
    //     Calendar c = Calendar.getInstance();
    //     c.setTime(d);
    //     c.add(Calendar.MONTH, 2);
    //     d = c.getTime();
        
    //     return Jwts.builder()
    //             .setAudience(owner)
    //             .setId(id)
    //             .setExpiration(d)
    //             .signWith(SignatureAlgorithm.HS512, key)
    //             .compact();
    // }

    public static Jws<Claims> decode(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    public static String getId(String token, String owner) {
        /** if token is ok return the id */
        try {
            Claims t = JWTUtil.decode(token).getBody();
            if (!t.getAudience().equals(owner)
                || new Date().after(t.getExpiration()))
                throw new TokenInvalidException();
            return t.getId();

        } catch (MalformedJwtException e) {
            throw new TokenInvalidException();
        } catch (SignatureException e) {
            throw new TokenInvalidException();
        }
    }
}