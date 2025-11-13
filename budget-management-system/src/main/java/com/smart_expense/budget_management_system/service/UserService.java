package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.exception.UserAlreadyExistException;
import com.smart_expense.budget_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public void saveUser(User user){
        Optional<User> user1= findUserByUserName(user.getUsername());
        if(user1.isEmpty()) {
            user.setCreatedDate(LocalDateTime.now());
            user.setLastModifiedDate(LocalDateTime.now());
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        else {
            throw new UserAlreadyExistException("User with this username already exists. Try with another UserName");
        }

    }

    public Optional<User> findUserByUserName(String username){
        return userRepository.findByUsername(username);
    }

}
