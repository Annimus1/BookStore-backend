package com.bookstore.Bookstore.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bookstore.Bookstore.exceptions.AccessDeniedHandlerException;
import com.bookstore.Bookstore.security.JwtAuthFilter;
import com.bookstore.Bookstore.security.Roles;

import lombok.RequiredArgsConstructor;

/**
 * Class that configures things related to HTTP requests
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AccessDeniedHandlerException accessDeniedHandlerException;

    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Configure the security of HTTP requests
     * 
     * @param http Request to configure
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http
            .exceptionHandling(
                exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandlerException))
            .sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(requests -> 
            requests
                .requestMatchers("/register", "/login", "/logout", "/api/book/**", "/api/genre/**",
                                "/api/author/**")
                .permitAll()
                // books
                .requestMatchers(HttpMethod.GET, "/api/book/**").hasAnyRole(Roles.USER, Roles.ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/auth/book/**").hasRole(Roles.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/auth/book/**").hasRole(Roles.ADMIN)
                // genre
                .requestMatchers(HttpMethod.GET, "/api/genre/**").hasAnyRole(Roles.USER, Roles.ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/auth/genre/**").hasRole(Roles.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/auth/genre/**").hasRole(Roles.ADMIN)
                // author
                .requestMatchers(HttpMethod.GET, "/api/book/**").hasAnyRole(Roles.USER, Roles.ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/auth/book/**").hasRole(Roles.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/auth/book/**").hasRole(Roles.ADMIN)
                // solo toma el primer filtro, ya no se puede anidar un rol con una autoridad
                // hasAuthority o hasRole para un solo rol/autoridad
                // hasAnyAuthority para varios roles
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}