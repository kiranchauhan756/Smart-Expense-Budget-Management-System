package com.smart_expense.budget_management_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                auth.requestMatchers("/","/home**","/signUp","/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser").permitAll()
                                .successHandler(((request, response, authentication) ->
                                {
                                    var authorities=authentication.getAuthorities();
                                    String redirectUrl="/user/dashboard";
                                    if(authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
                                        redirectUrl="/admin/home";
                                    }
                                    else if(authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_USER"))){
                                        redirectUrl="/user/dashboard";
                                    }
                                    response.sendRedirect(redirectUrl);
                                }))
                ).logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.permitAll().logoutUrl("/logout").logoutSuccessUrl("/login?logout"));
        return httpSecurity.build();
    }
}
