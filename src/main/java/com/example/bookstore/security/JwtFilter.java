package com.example.bookstore.security;

import org.springframework.stereotype.Component;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import com.example.bookstore.model.Customer;
import com.example.bookstore.service.CustomerService;
import com.example.bookstore.service.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomerService customerService;


    public JwtFilter(JwtUtil jwtUtil, CustomerService customerService) {
        this.jwtUtil = jwtUtil;
        this.customerService = customerService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // skips JWT Validation for login & register
        if (path.startsWith("/customers/register") || path.startsWith("/customers/login")) {
        filterChain.doFilter(request, response);
        return;
        }


        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.isTokenValid(token)) {
   //extract email from token
                String email = jwtUtil.extractEmail(token);
                
    //IMPORTANT--//extract role from token
                String role =jwtUtil.extractRole(token); 

                Customer customer = customerService.findByEmail(email);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                customer, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
