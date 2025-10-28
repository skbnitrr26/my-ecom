package com.sumitcoder.config;

import java.util.Arrays;
import java.util.List; // Ensure this import is present

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Ensure this import is present
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Keep CSRF disabled
        .csrf(csrf -> csrf.disable())

        // Keep CORS enabled using your existing Bean
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // TEMPORARILY ALLOW ALL REQUESTS - NO SECURITY
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
        );

    // REMOVE session management and JWT filter FOR THIS TEST

    return http.build();
}
    // CORS Configuration Bean
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(Arrays.asList(
                "https://sam-market-zeta.vercel.app",
                "https://sam-kart.netlify.app", 
                "http://localhost:3000",
                "http://localhost:5173"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); 
        configuration.setAllowCredentials(true); 
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        configuration.setExposedHeaders(Arrays.asList("Authorization")); 
        configuration.setMaxAge(3600L); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}