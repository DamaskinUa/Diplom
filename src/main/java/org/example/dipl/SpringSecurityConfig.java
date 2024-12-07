package org.example.dipl;

import org.example.dipl.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("spring-security")
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                   .requestMatchers("/login", "/register", "/catalog/**", "/titles/**", "/css/**", "/images/**","/uploads/**")
                   .permitAll() // Allow everyone to access login, register, catalog, and images
                   .requestMatchers("/profile/**","/marker").hasAnyRole("USER", "ADMIN") // Allow access to profile for both users and admins
                   .requestMatchers("/admin/**").hasRole("ADMIN") // Only admins can access /admin/** endpoints
                   .anyRequest().authenticated() // All other requests require authentication
                .and()
                .formLogin()
                    .loginPage("/login") // Custom login page
                    .defaultSuccessUrl("/profile", true) // Redirect to /profile after login
                    .permitAll() // Allow access to login page for everyone
                .and()
                .logout()
                     .logoutUrl("/logout") // Standard logout URL
                     .logoutSuccessUrl("/catalog") // Redirect to catalog after logout
                     .invalidateHttpSession(true) // Invalidate the session on logout
                     .clearAuthentication(true)
                     .deleteCookies("JSESSIONID") // Delete the JSESSIONID cookie
                .permitAll(); // Allow access to logout for everyone

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService); // Use our custom UserDetailsService
        return authenticationManagerBuilder.build();
    }
}