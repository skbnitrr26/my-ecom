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

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        
                        // Rule 1: Allow all OPTIONS requests (for CORS preflight)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                        
                        // Rule 2: Allow specific public API endpoints
                        .requestMatchers("/api/products/*/reviews").permitAll()
                        
                        // Rule 3: Allow all authentication, home, coupon endpoints etc.
                        .requestMatchers("/auth/**").permitAll() 
                        .requestMatchers("/home/**").permitAll() 
                        .requestMatchers("/coupons/**").permitAll() 
                        
                        // Rule 4: Secure the rest of the /api endpoints
                        .requestMatchers("/api/**").authenticated() 
                        
                        // Rule 5: Allow any other requests (like root /, static assets if not handled elsewhere)
                        .anyRequest().permitAll() 
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                // Only one .cors() call, using the Bean defined below
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); 

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