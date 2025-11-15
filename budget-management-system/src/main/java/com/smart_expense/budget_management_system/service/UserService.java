package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.Role;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.exception.UserAlreadyExistException;
import com.smart_expense.budget_management_system.exception.UserNotFoundException;
import com.smart_expense.budget_management_system.repository.RoleRepository;
import com.smart_expense.budget_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService (UserRepository userRepository,PasswordEncoder passwordEncoder,RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }

    public void saveUser(User user){
        Optional<User> user1= findUserByUserName(user.getUsername());
        if(user1.isEmpty()) {
            user.setCreatedDate(LocalDateTime.now());
            user.setLastModifiedDate(LocalDateTime.now());
            user.setActive(true);
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found in DB"));
            user.addRoles(userRole);
            if(user.getPassword() == null || user.getPassword().isBlank()){
                user.setPassword(passwordEncoder.encode("user"));

            }
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

    public Integer getTotalUsers(){
        if(userRepository.count()!=0)return Math.toIntExact(userRepository.count());
        throw new UserNotFoundException("No users are present in the system yet..");
    }

    public void updateUser(long id,User user){
        Optional<User> user1=findUserById(id);
        if(user1.isPresent()) {
            user1.get().setLastModifiedDate(LocalDateTime.now());
            user1.get().setUsername(user.getUsername());
            user1.get().setEmail(user.getEmail());
            user1.get().setActive(user.isActive());
            userRepository.save(user1.get());

        }

    }
    public void deleteUser(long id){
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty())throw new UserNotFoundException("No User found with this id");
        else{
            userRepository.deleteById(id);
        }
    }
    public Optional<User> findUserById(long id){
        if(userRepository.findById(id).isEmpty())throw new UserNotFoundException("No User found with this id");
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        List<User> userList=userRepository.findAll();
        if(userList.isEmpty()) throw new UserNotFoundException("No users are present in the system yet..");
       return userList;
    }
}
