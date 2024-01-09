package org.workintech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource(corsConfigurationSource());
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET,"/products/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/product/**").hasAnyAuthority("admin","store");
                    auth.requestMatchers(HttpMethod.PUT, "/product/**").hasAnyAuthority("admin","store");
                    auth.requestMatchers(HttpMethod.DELETE, "/product/**").hasAnyAuthority("admin","store");

                    auth.requestMatchers(HttpMethod.GET,"/categories/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/categories/**").hasAnyAuthority("admin");
                    auth.requestMatchers(HttpMethod.PUT, "/categories/**").hasAnyAuthority("admin");
                    auth.requestMatchers(HttpMethod.DELETE, "/categories/**").hasAnyAuthority("admin");

                    auth.requestMatchers(HttpMethod.GET,"/roles/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/roles/**").hasAnyAuthority("admin");
                    auth.requestMatchers(HttpMethod.PUT, "/roles/**").hasAnyAuthority("admin");
                    auth.requestMatchers(HttpMethod.DELETE, "/roles/**").hasAnyAuthority("admin");

                    auth.requestMatchers("/address/**").permitAll();

                    auth.requestMatchers("/signup/**").permitAll();
                    auth.requestMatchers("/login/**").permitAll();
                    auth.requestMatchers("/verify/**").permitAll();
                    auth.requestMatchers("/user/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
