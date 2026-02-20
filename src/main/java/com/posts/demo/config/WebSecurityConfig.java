package com.posts.demo.config;


import com.posts.demo.filters.JwtAuthFilter;
import org.springframework.boot.security.autoconfigure.actuate.web.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
   http
           .authorizeHttpRequests(auth-> auth
                   .requestMatchers("/api/v1/auth/**","/error","/actuator/**").permitAll()
                   .requestMatchers("/posts/**").permitAll().anyRequest().authenticated())
           .csrf(AbstractHttpConfigurer::disable)
           .sessionManagement(sessionManagementConfig->sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
           //.formLogin(Customizer.withDefaults());
   return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
