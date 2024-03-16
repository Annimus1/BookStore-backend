package com.bookstore.Bookstore.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookstore.Bookstore.exceptions.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter that validates if the request has the Authorization header
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtAuthFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    /**
     * List of URIs that do not require authentication
     */
    private List<String> urlsToSkip = List.of("/register", "/login", "/logout",
            "/api/book/", "/api/book/**",
            "/api/genre/", "/api/genre/**",
            "/api/author/", "/api/author/**");

    /**
     * Check if the filter should not be applied to the URI
     * 
     * @param request current HTTP request Request to validate
     * @return True the URI exists in the whitelist, false otherwise
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("-------------------------------------------------------------");
        System.out.println("ShouldNotFilter");
        return urlsToSkip.stream().anyMatch(url -> request.getRequestURI().contains(url));
    }

    /**
     * Validates if the request contains the authorization header with the bearer 
     * token
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     * @throws UnauthorizedException - If it does not have the HttpHeaders.AUTHORIZATION header 
     *                               - If it has more than two elements in the header or does not have 'Bearer' 
     *                               - If the token is not valid
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            throw new UnauthorizedException();
        }

        String[] authElements = header.split(" ");

        if (authElements.length != 2 || !"Bearer".equals(authElements[0])) {
            throw new UnauthorizedException();
        }

        try {
            Authentication auth = jwtAuthenticationProvider.validateToken(authElements[1]);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            System.out.println(SecurityContextHolder.getContext());
            
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            throw new RuntimeException(e);
        }
        
        filterChain.doFilter(request, response);
    }
}