package com.example.sd_frateanandreea_backend2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            if (jwtService.isTokenValid(jwt)) {
                String role = jwtService.extractUserRole(jwt);

                if ("ADMIN".equals(role)) {
                    Authentication auth = new PreAuthenticatedAuthenticationToken(null, null, Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else if ("CLIENT".equals(role)){
                    Authentication auth = new PreAuthenticatedAuthenticationToken(null, null, Collections.singletonList(new SimpleGrantedAuthority("CLIENT")));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
