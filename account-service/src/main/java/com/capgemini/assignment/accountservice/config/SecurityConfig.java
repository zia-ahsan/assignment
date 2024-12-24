package com.capgemini.assignment.accountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final Environment environment;

    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (isTestProfileActive()) {
            // In test mode, permit all requests
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } else {
            // In non-test mode, configure endpoint-specific access
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/accounts/**").permitAll()
                    .anyRequest().authenticated());
        }

        // Disable CSRF and frame options for H2 console access
        http.csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    private boolean isTestProfileActive() {
        // Check if the current profile is "test"
        return environment.getActiveProfiles() != null &&
                environment.acceptsProfiles("test");
    }
}
