package com.bookstore.Bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bookstore.Bookstore.security.JwtAuthFilter;
import com.bookstore.Bookstore.security.JwtAuthenticationProvider;

/**
 * Configuration class for creating Beans to use
 */
@Configuration
public class ApplicationConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public ApplicationConfig(JwtAuthenticationProvider jwtAuthenticationProvider){
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    /**
     * Password Encoder Bean for injection
     * @return Implementation BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JwtAuthFilter Bean for injection
     * @return Implementation JwtAuthFilter
     */
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtAuthenticationProvider);
    }

}
