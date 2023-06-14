package com.shubh.roamify_services.JwtFiles;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;



@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     
                      
   http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/v1/api/user/signup","/v1/api/auth/**").
            permitAll()
            .anyRequest().authenticated()
        )
                  
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider).
            addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        


            // http.csrf().disable()
            //     .authorizeHttpRequests()
            //     .requestMatchers("/v1/api/auth/login").permitAll()
            //     .anyRequest()
            //     .authenticated().and()
            //     .sessionManagement()
            //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            //     .and()
            //     .authenticationProvider(authenticationProvider)
            //     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    
  }
  
}
