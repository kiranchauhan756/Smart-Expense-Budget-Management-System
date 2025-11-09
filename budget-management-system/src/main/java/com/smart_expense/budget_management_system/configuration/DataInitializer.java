package com.smart_expense.budget_management_system.configuration;

import com.smart_expense.budget_management_system.entity.Role;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.repository.RoleRepository;
import com.smart_expense.budget_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepository,UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<Role>userRole= Optional.of(roleRepository.findByName("USER").orElseGet(() ->
                roleRepository.save(new Role("USER", "Default role for users"))));
        Optional<Role> adminRole= Optional.of(roleRepository.findByName("ADMIN").orElseGet(() ->
                roleRepository.save(new Role("ADMIN", "Role for admins"))));
        if(userRepository.findByUsername("budgetIQAdmin").isEmpty()){
            User user=new User("budgetIQAdmin@gmail.com", passwordEncoder.encode( "admin123"), "budgetIQAdmin", true, LocalDateTime.now(),LocalDateTime.now());
            user.setRoles(List.of(userRole.get(),adminRole.get()));
            userRepository.save(user);
        }
    }
}
