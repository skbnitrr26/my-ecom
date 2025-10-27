package com.sumitcoder.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List; // Ensure this import is present

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <-- ADD THIS IMPORT
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <-- Using this for the Bean approach

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        
                        // FIX 1: Allow all OPTIONS requests for CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                        
                        // FIX 2: Order rules from most specific to least specific
                        .requestMatchers("/api/products/*/reviews").permitAll()
                        .requestMatchers("/auth/**").permitAll() // Assuming your auth endpoints are here
                        .requestMatchers("/home/**").permitAll() // Assuming your home endpoints are here
                        .requestMatchers("/coupons/**").permitAll() // Assuming coupon endpoints are public
                        .requestMatchers("/api/**").authenticated() // Secure the rest of the API
                        
                        .anyRequest().permitAll() // Allow other non-API requests (if any)
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Use the Bean below

        return http.build();
    }

    // CORS Configuration Bean (Slightly improved structure)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Define allowed origins
        configuration.setAllowedOrigins(Arrays.asList(
                "https://sam-market-zeta.vercel.app",
                "https://sam-kart.netlify.app", // Keep old one just in case
                "http://localhost:3000",
                "http://localhost:5173"
        ));
        
        // Allow all standard methods including OPTIONS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); 
        
        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true); 
        
        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        
        // Expose the Authorization header so frontend can read JWTs
        configuration.setExposedHeaders(Arrays.asList("Authorization")); 
        
        // How long the results of a preflight request can be cached
        configuration.setMaxAge(3600L); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all paths
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