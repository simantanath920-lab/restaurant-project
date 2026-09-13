package com.simanta.restaurant_backend.securtity;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.simanta.restaurant_backend.service.Custom_UserDetails_Service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends  OncePerRequestFilter{

    private final JwtToken jwtToken;
    private final Custom_UserDetails_Service custom_UserDetails_Service;

    public JwtFilter(JwtToken jwtToken,Custom_UserDetails_Service custom_UserDetails_Service) {
        this.jwtToken = jwtToken;
        this.custom_UserDetails_Service = custom_UserDetails_Service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            final String jwt = authHeader.substring(7);

            final String email = jwtToken.extractEmail(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = custom_UserDetails_Service.loadUserByUsername(email);

                if (jwtToken.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception ex) {

            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            logger.error("JWT Authentication failed", ex);

            return;
        }

        filterChain.doFilter(request, response);
    
    }
}
