package com.example.moduleone.filters;

import com.example.moduleone.models.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

public class CreateJWT {

    private JWTInfo jwtInfo = new JWTInfo();

    public CreateJWT() { }

    public String createAccessToken(UserEntity user) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtInfo.getSecretKey().getBytes());

        String jws = Jwts.builder()
                .claim("netid", user.getNetid())
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + 10*60*1000))
                .signWith(secretKey)
                .compact();
        return "Bearer " + jws;
    }

    public String createRefreshToken(UserEntity user) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtInfo.getSecretKey().getBytes());

        String jws = Jwts.builder()
                .claim("netid", user.getNetid())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*1000))
                .signWith(secretKey)
                .compact();
        return "Bearer " + jws;
    }

}
