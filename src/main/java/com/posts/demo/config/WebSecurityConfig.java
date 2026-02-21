package com.posts.demo.config;


import com.posts.demo.filters.JwtAuthFilter;
import com.posts.demo.handler.OAuth2SuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
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
    private final OAuth2SuccessHandler  oAuth2SuccessHandler;

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter, OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
   http
           .authorizeHttpRequests(auth-> auth
                   .requestMatchers("/api/v1/auth/**","/error","/actuator/**","/home.html").permitAll()
                   .requestMatchers("/posts/**").permitAll().anyRequest().authenticated())
//           .exceptionHandling(exceptions -> exceptions
//                   .authenticationEntryPoint((request, response, authException) -> {
//                       response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                   })
//           )
           .csrf(AbstractHttpConfigurer::disable)
           .sessionManagement(sessionManagementConfig->sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
           .oauth2Login(oauth2config->oauth2config.failureUrl("/login?error=true")
                           .successHandler(oAuth2SuccessHandler)
                   );
           //.formLogin(Customizer.withDefaults());
   return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
