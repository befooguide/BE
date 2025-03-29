package com.befoo.befoo.config;

import com.befoo.befoo.domain.dto.CustomUserDetails;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;

@TestConfiguration
@EnableWebSecurity
public class SecurityTestConfig {

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChainForTest(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new TestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    
    @Bean
    @Primary
    public UserDetailsService testUserDetailsService() {
        return username -> {
            User testUser = User.builder()
                    .username("테스트사용자")
                    .email("test@example.com")
                    .role(Role.ROLE_USER)
                    .healthConditions(new ArrayList<>())
                    .allergies(new ArrayList<>())
                    .build();
            return new CustomUserDetails(testUser);
        };
    }
} 