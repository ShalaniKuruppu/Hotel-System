//CORS = Cross-Origin Resource Sharing
//It controls which frontend websites are allowed to send requests to your backend.



package com.example.HotelServer.configs;

import org.springframework.stereotype.Component;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                HttpServletRequest httphrequest = (HttpServletRequest) request;
                String originHeader = httphrequest.getHeader("origin");
                httpResponse.setHeader("Access-Control-Allow-Origin", originHeader);
                httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
                httpResponse.setHeader("Access-Control-Max-Age", "3600");
                httpResponse.setHeader("Access-Control-Allow-Headers", "*");

                if ("OPTIONS".equalsIgnoreCase(httphrequest.getMethod())) {
                    httpResponse.setStatus(HttpServletResponse.SC_OK);
                } else {
                    chain.doFilter(request, response);
                }
        
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
