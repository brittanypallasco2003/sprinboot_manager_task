package com.brittany.sprinboot_manager_task.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brittany.sprinboot_manager_task.config.security.CustomAccessDeniedHandler;
import com.brittany.sprinboot_manager_task.config.security.CustomAuthenticationEntryPoint;
import com.brittany.sprinboot_manager_task.config.security.JwtTokenValidator;
import com.brittany.sprinboot_manager_task.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtils jwtUtils;
    private final CustomAuthenticationEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtils jwtUtils, CustomAuthenticationEntryPoint authEntryPoint,
             CustomAccessDeniedHandler accessDeniedHandler) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtils = jwtUtils;
        this.authEntryPoint=authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .build();
    }


    // @Bean
    // AuthenticationEntryPoint customAuthenticationEntryPoint() {
    //     return new AuthenticationEntryPoint() {
    //         @Override
    //         public void commence(HttpServletRequest request, HttpServletResponse response,
    //                 AuthenticationException authException) {
    //             try {
    //                 response.setContentType("application/json");
    //                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    //                 var body = new java.util.HashMap<String, Object>();
    //                 body.put("status", HttpStatus.UNAUTHORIZED.name());
    //                 body.put("message", authException.getMessage());
    //                 body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    //                 new ObjectMapper().writeValue(response.getOutputStream(), body);
    //             } catch (Exception e) {
    //                 throw new RuntimeException(e);
    //             }
    //         }

    //     };
    // }

}
