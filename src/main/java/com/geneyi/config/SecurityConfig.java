package com.geneyi.config;

import com.geneyi.domain.member.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth
                            .requestMatchers(antMatcher("/login")).permitAll()
                            .requestMatchers(antMatcher("/api/**/menus/**")).hasRole(Role.ADMIN.getRole())
                            .requestMatchers(antMatcher("/api/**/todos/**")).hasRole(Role.USER.getRole())
                            .anyRequest().authenticated())
                .formLogin(
                        login -> login.loginPage("/login")
                );
        return http.build();
    }
}
