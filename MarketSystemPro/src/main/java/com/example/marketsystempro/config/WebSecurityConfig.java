package com.example.marketsystempro.config;

import com.company.marketsystem.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;
    private final UnAuthorizedHandler unAuthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .csrf().disable().cors().configurationSource(corsConfigurationSource()).and()
//                .csrf().disable().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .exceptionHandling().authenticationEntryPoint(unAuthorizedHandler).and()
                .securityMatcher("/**")
                .authorizeHttpRequests((registry -> registry.
                        requestMatchers("/auth/**").permitAll().
                        requestMatchers("/api/product/save").permitAll().
                        requestMatchers("/api/product/update/{barCode}/{name}/{productCategory}/{price}/{id}").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/product/delete/{barCode}").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/product/byCategory/{productCategory}").permitAll().
                        requestMatchers("/api/product/byName/{name}").permitAll().
                        requestMatchers("/api/product/byBarCode/{barCode}").permitAll().
                        requestMatchers("/api/product/{firstPrice}/{endPrice}").permitAll().
                        requestMatchers("/api/product/products").permitAll().
                        requestMatchers("/h2-console").permitAll().
                        requestMatchers("/api/sale/save").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/sale/update//{salesPrice}/{salesItems}/{salesDate}/{salesNumber}").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/sale/delete/{salesNumber}").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/sale/returnAll/{salesItems}").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/sale/returnSales/{salesItem}/{salesNumber}").hasAnyRole("ADMIN","SUPERVISOR").
                        requestMatchers("/api/sale/{localDate}").permitAll().
                        requestMatchers("/api/sale/byPrice/{firstPrice}/{endPrice}").permitAll().
                        requestMatchers("/api/sale/sales").permitAll().
                        requestMatchers("/api/sale/byTime/{startTime}/{endTime}").permitAll().
                        requestMatchers("/api/sale/bySalesNumber/{salesNumber}").permitAll().
                        anyRequest().authenticated()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5501"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}
