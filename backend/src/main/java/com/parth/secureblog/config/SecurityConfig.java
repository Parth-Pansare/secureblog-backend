package com.parth.secureblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow ONLY the Vite frontend origin
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
        // Allow ALL methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        // Allow ALL headers
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        // Allow credentials (necessary for certain JWT or cookie setups)
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Apply CORS settings to Spring Security filter chain
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 2. Disable CSRF for stateless JWT APIs
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/comments/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/comments/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/comments/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 3. Add JWT Filter before Security Filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
