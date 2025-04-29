package com.spring.elobaby.config.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring.elobaby.exceptions.JwtTokenInvalidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    final private Logger LOG = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        try {
            if (token == null) {
                chain.doFilter(request, response);
                return;
            }
            DecodedJWT jwt;
            if (!token.startsWith("Bearer ")) {
                throw new JwtTokenInvalidException("Authentication schema not found");
            }
            token = token.substring(7);
            if (token.isEmpty()) {
                throw new JwtTokenInvalidException("No token found");
            }
            try {
                jwt = jwtTokenUtil.decodeToken(token);
            } catch (SignatureVerificationException e) {
                throw new JwtTokenInvalidException("Invalid or expired token");
            }

            if (jwt != null && SecurityContextHolder.getContext()
                    .getAuthentication() == null) {
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
                authentication.setAuthenticated(true);
                response.setHeader("Authorization", "Bearer " + token);
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        }
        catch (AuthenticationException e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            return;
        }
        chain.doFilter(request, response);

    }

}
