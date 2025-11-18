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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
            if(user.getProfilePicture() == null || user.getProfilePicture().isBlank()) {
                user.setProfilePicture("account.png");
            }
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found in DB"));
            user.addRoles(userRole);
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

    public boolean checkOldPassword(Long id, String password) {
        Optional<User> user=findUserById(id);
        if(user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPassword());
        }
        throw new UserNotFoundException("No User found with this id");
    }

    public void updateUser(User formUser,
                           MultipartFile profilePic,
                           String oldPassword,
                           String newPassword,
                           String confirmPassword) throws IOException {

        User existingUser = userRepository.findById(formUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setEmail(formUser.getEmail());
        existingUser.setAlternateEmail(formUser.getAlternateEmail());
        existingUser.setGender(formUser.getGender());
        existingUser.setPhoneNumber(formUser.getPhoneNumber());

        if (formUser.getDob() != null) {
            existingUser.setDob(formUser.getDob());
        }


        // ---- PROFILE PIC ----
        if (profilePic != null && !profilePic.isEmpty()) {

            String fileName = existingUser.getId() + "_" + profilePic.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images/" + fileName);

            Files.copy(profilePic.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            existingUser.setProfilePicture(fileName);
        }

        // ---- PASSWORD CHANGE ----
        // Only change if oldPassword is given
        if (oldPassword != null && !oldPassword.isEmpty()) {

            // 1. Old password must match existing one
            if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
                throw new RuntimeException("Old password is incorrect!");
            }

            // 2. newPassword must not be empty
            if (newPassword == null || newPassword.isEmpty()) {
                throw new RuntimeException("New password cannot be empty!");
            }

            // 3. newPassword and confirmPassword must match
            if (!newPassword.equals(confirmPassword)) {
                throw new RuntimeException("New and confirm password do not match!");
            }

            // 4. Save only newPassword (encoded)
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        // --- SAVE ---
        userRepository.save(existingUser);
    }

}
