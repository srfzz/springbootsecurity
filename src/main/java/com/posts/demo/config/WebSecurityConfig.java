package com.posts.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
   http
           .authorizeHttpRequests(auth-> auth.requestMatchers("/posts/**").permitAll().anyRequest().authenticated())
           .csrf(AbstractHttpConfigurer::disable)
           .sessionManagement(sessionManagementConfig->sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           //.formLogin(Customizer.withDefaults());
   return http.build();
    }
}
