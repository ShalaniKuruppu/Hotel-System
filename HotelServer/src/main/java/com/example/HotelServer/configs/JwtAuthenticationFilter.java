// This is a JWT authentication filter.
// It runs before every request in your backend to:
//     Read the JWT token from the request header
//     Validate the token
//     Identify the user
//     Tell Spring Security that the user is authenticated
// It extends OncePerRequestFilter, so it runs exactly once for every HTTP request.

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
        if(StringUtils.isEmpty(authHeader) || StringUtils.startsWith(authHeader, "Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt;
        jwt = authHeader.substring(7);
        final String userEmail;
        userEmail = jwtUtil.extractUserName(jwt);
        if(StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if(jwtUtil.isTokenValid(jwt, userDetails)){
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
            filterChain.doFilter(request, response);
        }

    }
}