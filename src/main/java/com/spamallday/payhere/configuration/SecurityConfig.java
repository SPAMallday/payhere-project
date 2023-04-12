package com.spamallday.payhere.configuration;

import com.spamallday.payhere.configuration.auth.JwtAuthenticationFilter;
import com.spamallday.payhere.configuration.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()              // httpBasic 비활성화
                .csrf().disable()                   // CSRF 비활성화
                .cors().and()                       // CORS 활성화
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)         // 세션 관리 없이 token으로 인증
                    .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/members/login").permitAll()
                    .antMatchers("/members/test").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
