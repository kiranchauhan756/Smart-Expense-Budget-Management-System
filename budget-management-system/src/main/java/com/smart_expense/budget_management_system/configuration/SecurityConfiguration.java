package com.smart_expense.budget_management_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws  Exception{
        httpSecurity.authorizeHttpRequests(auth->
                auth.requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/login").permitAll())
                .logout(LogoutConfigurer::permitAll);
        return httpSecurity.build();
    }
}
