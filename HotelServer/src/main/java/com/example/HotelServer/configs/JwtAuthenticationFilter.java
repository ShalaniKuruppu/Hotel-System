package com.example.HotelServer.configs;

import com.example.HotelServer.services.jwt.UserService;
import com.example.HotelServer.utill.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        
        System.out.println("=== JWT Filter START ===");
        System.out.println("Auth Header: " + authHeader);
        
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer")){
            System.out.println("No Bearer token - skipping JWT authentication");
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt;
        jwt = authHeader.substring(7);
        final String userEmail;
        userEmail = jwtUtil.extractUserName(jwt);
        
        System.out.println("=== JWT Filter ===");
        System.out.println("Email from token: " + userEmail);
        
        if(StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            System.out.println("User authorities: " + userDetails.getAuthorities());
            
            if(jwtUtil.isTokenValid(jwt, userDetails)){
                System.out.println("Token is VALID");
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else {
                System.out.println("Token is INVALID");
            }
        }
        filterChain.doFilter(request, response);
    }
}