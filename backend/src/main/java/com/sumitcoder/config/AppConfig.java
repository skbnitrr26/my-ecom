package com.sumitcoder.config; // Use your actual package name

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Required for HttpMethod.OPTIONS
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
            // Disable session management (stateless)
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Define authorization rules
            .authorizeHttpRequests(Authorize -> Authorize

                    // Rule 1: Allow all OPTIONS requests (for CORS preflight) - CRITICAL
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // Rule 2: Allow Actuator health endpoint for Render
                    .requestMatchers("/actuator/health").permitAll()

                    // Rule 3: Allow specific public API endpoints (adjust paths if needed)
                    .requestMatchers("/api/products/*/reviews").permitAll()

                    // Rule 4: Allow public endpoints (auth, home, etc. - adjust paths if needed)
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/home/**").permitAll()
                    .requestMatchers("/coupons/**").permitAll()

                    // Rule 5: Secure the rest of the /api endpoints
                    .requestMatchers("/api/**").authenticated()

                    // Rule 6: Allow any other requests (like root /, static assets if not handled elsewhere)
                    .anyRequest().permitAll()
            )
            // Add your custom JWT validation filter
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)

            // Disable CSRF protection (common for stateless APIs)
            .csrf(csrf -> csrf.disable())

            // Enable CORS using the bean defined below
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // CORS Configuration Bean
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // **Point 1: Ensure Vercel URL is present**
        configuration.setAllowedOrigins(Arrays.asList(
                "https://sam-market-zeta.vercel.app", // Your Vercel frontend
                "https://sam-kart.netlify.app",    // Your old Netlify frontend (optional)
                "http://localhost:3000",             // Local development
                "http://localhost:5173"              // Another possible local dev port
        ));

        // Allow all standard HTTP methods + OPTIONS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Allow credentials (needed for cookies, authorization headers like JWT)
        configuration.setAllowCredentials(true);

        // Allow all headers in requests
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Expose the Authorization header so the frontend can read the JWT from responses
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // How long the browser can cache the preflight response
        configuration.setMaxAge(3600L); // 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all paths ("/**")
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