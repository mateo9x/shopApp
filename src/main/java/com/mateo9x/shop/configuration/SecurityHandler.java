package com.mateo9x.shop.configuration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@PropertySource(value = {"classpath:application.properties"})
public class SecurityHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTime}")
    private String expirationTime;
    

    @Override
    @Deprecated
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
      UserDetails principal = (UserDetails) authentication.getPrincipal(); // 1
      String token = JWT.create() // 2
        .withSubject(principal.getUsername()) // 3
        .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 4
        .sign(Algorithm.HMAC256(secret)); // 5
      response.getOutputStream().print("{\"token\": \"" + token + "\"}"); // 6
    }
    
}
