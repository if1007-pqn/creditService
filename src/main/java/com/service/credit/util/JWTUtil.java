package com.service.credit.util;

// import java.util.Calendar;
import java.util.Date;

import com.service.credit.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JWTUtil {


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

    public static Jws<Claims> decode(String key, String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    public static String getId(String key, String token, String owner) {
        /** if token is ok return the id */
        try {
            Claims t = JWTUtil.decode(key, token).getBody();
            if (!t.getAudience().equals(owner)
                || new Date().after(t.getExpiration()))
                throw new InvalidTokenException();
            return t.getId();

        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (SignatureException e) {
            throw new InvalidTokenException();
        }
    }
}