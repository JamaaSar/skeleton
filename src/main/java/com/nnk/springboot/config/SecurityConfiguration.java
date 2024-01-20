package com.nnk.springboot.config;

import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserService userService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers( "/", "app/login").permitAll();
                    auth.requestMatchers("/user/**", "/app/secure/article-details").hasAuthority("ADMIN");
                    auth.requestMatchers("/trade/**", "/ruleName/**","/rating/**","/curvePoint/**","/bidList/**").hasAuthority("USER");
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                    .successHandler((request, response, authentication) -> {
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            if (authorities.contains(new SimpleGrantedAuthority("USER"))) {
                                response.sendRedirect("/bidList/list");
                            } else if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
                                response.sendRedirect("/user/list");
                            }
                        })
                    )
                .logout((logout) -> logout
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"))
                )
                .sessionManagement(sessionManagement -> { sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation().migrateSession();
                })
               .build();
        }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configuring user details service and password encoder
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

}
