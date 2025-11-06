package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void saveUser(User user){
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
