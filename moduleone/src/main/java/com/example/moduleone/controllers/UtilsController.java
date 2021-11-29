package com.example.moduleone.controllers;


import com.example.moduleone.filters.CreateJWT;
import com.example.moduleone.filters.JWTInfo;
import com.example.moduleone.models.UserEntity;
import com.example.moduleone.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/")
public class UtilsController {

    private final JWTInfo jwtInfo = new JWTInfo();
    private final CreateJWT createJWT = new CreateJWT();
    private final UserService userService;

    public UtilsController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTHORIZATION); //refresh_token
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authHeader.substring("Bearer ".length());

                SecretKey secretKey = Keys.hmacShaKeyFor(jwtInfo.getSecretKey().getBytes());
                Jws<Claims> jws;
                jws = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(refreshToken);
                Claims body = jws.getBody();

                String netid = body.get("netid", String.class);
                UserEntity user =  userService.findUserByNetid(netid);
                String accessToken = createJWT.createAccessToken(user);

                response.setHeader(AUTHORIZATION, accessToken);
                response.setHeader("RefreshToken", authHeader);
            } catch (JwtException ex) {
                throw new RuntimeException("Token is not valid");
            }
        } else {
            throw new RuntimeException("Refresh token is missing.");
        }
    }

    @GetMapping("authorize")
    public ResponseEntity<Object> authorize() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
