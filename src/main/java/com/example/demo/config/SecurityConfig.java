package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors().and()  // Enable CORS
            .authorizeHttpRequests()
            .requestMatchers("/**").permitAll()  // Allow public access for all endpoints
            .anyRequest().authenticated() // All other requests must be authenticated
            .and()
            .oauth2Login() // Enable OAuth2 login, which will use Google
            .and()
            .logout()
            .permitAll(); // Allow logout

        return http.build();
    }
}
