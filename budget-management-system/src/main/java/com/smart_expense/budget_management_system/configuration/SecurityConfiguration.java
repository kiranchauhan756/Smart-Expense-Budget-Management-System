package com.smart_expense.budget_management_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws  Exception{
        httpSecurity.authorizeHttpRequests(auth->
                auth.requestMatchers("/","/signUp","/signUp/**","/user","/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser").permitAll()
                                .successHandler(((request, response, authentication) ->
                                {
                                    var authorities=authentication.getAuthorities();
                                    String redirectUrl="/dashboard/user";
                                    if(authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
                                        redirectUrl="/dashboard/admin";
                                    }
                                    else if(authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_USER"))){
                                        redirectUrl="/dashboard/user";
                                    }
                                    response.sendRedirect(redirectUrl);
                                }))
                ).logout(LogoutConfigurer::permitAll);
        return httpSecurity.build();
    }
}
