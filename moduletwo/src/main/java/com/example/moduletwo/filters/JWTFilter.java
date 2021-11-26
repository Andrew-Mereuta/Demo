package com.example.moduletwo.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class JWTFilter extends OncePerRequestFilter {

    private JWTInfo jwtInfo = new JWTInfo();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            String authHeader = request.getHeader(AUTHORIZATION);

            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    String token = authHeader.substring("Bearer ".length());

                    SecretKey secretKey = Keys.hmacShaKeyFor(jwtInfo.getSecretKey().getBytes());
                    Jws<Claims> jws;
                    jws = Jwts.parserBuilder()
                            .setSigningKey(secretKey)
                            .build()
                            .parseClaimsJws(token);
                    Claims body = jws.getBody();

                    String netid = body.get("netid", String.class);
                    List<String> roles = (ArrayList<String>) body.get("roles");
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    roles.forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(netid, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (JwtException ex) {
                    response.sendError(FORBIDDEN.value());
                    throw new RuntimeException(ex.getMessage());
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}