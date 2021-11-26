package com.example.moduleone.filters;

import com.example.moduleone.models.UserEntity;
import com.example.moduleone.models.UserRequest;
import com.example.moduleone.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTPublisher extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CreateJWT construct = new CreateJWT();

    public JWTPublisher(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String n = new String(request.getInputStream().readAllBytes());
            UserRequest authenticationClientRequest = new ObjectMapper()
                    .readValue(n, UserRequest.class);

            Authentication authenticate = new UsernamePasswordAuthenticationToken(
                    authenticationClientRequest.getNetid(),
                    authenticationClientRequest.getPassword()
            );

            Authentication auth = authenticationManager.authenticate(authenticate);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserEntity user = (UserEntity) authResult.getPrincipal();
        String accessToken = construct.createAccessToken(user);
        String refreshToken = construct.createRefreshToken(user);

        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        response.setHeader("RefreshToken", refreshToken);
    }

}
