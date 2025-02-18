package com.example.demo.config.auth;

import com.example.demo.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@RequiredArgsConstructor

public class SecurityConfig  {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(e->e.disable())
                .headers(h->h.frameOptions(e-> e.disable()))
                .logout(lg->lg.logoutSuccessUrl("/"))
                .oauth2Login(ol->ol.userInfoEndpoint(uie->uie.userService(customOAuth2UserService)));
        // 인가(접근권한) 설정
        http.authorizeHttpRequests((requests)-> requests
                .requestMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/profile").permitAll()
                .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated());

        return http.build();
    }

}
