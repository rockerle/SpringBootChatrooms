package com.example.chatserver.configuration;

import com.example.chatserver.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    AuthUserDetailsService userDetailService;

    @Autowired
    public SecurityConfig(AuthUserDetailsService userDetailService){
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authRequest ->{
//                    authRequest.requestMatchers("/","/index","/register","/test/**").permitAll()
//                            .requestMatchers("/chat/**").authenticated()
//                            .anyRequest().denyAll();
            authRequest.requestMatchers("/chat/**","/createroom", "/joinroom", "/messageIn/**","/broadcast/**").authenticated();
            authRequest.requestMatchers("/webjars/**","/static/**").authenticated();
            authRequest.requestMatchers("/","index","register","/login").permitAll();
                })
                .formLogin(form ->
                        form.defaultSuccessUrl("/chat").failureForwardUrl("/login"))
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
