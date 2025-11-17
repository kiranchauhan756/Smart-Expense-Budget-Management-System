package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin/settings")
public class AdminSettingsController {
   private final UserService userService;
   private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSettingsController(UserService userService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping
    public String showAdminSettingsPage(Principal principal, Model model,
                                        @ModelAttribute("user") User user, @RequestParam("profilePic")MultipartFile profilePic,
                                        @RequestParam(value="oldPassword",required = false)String oldPassword,
                                        @RequestParam(value="newPassword",required = false)String newPassword,
                                        @RequestParam(value="confirmPassword",required = false) String confirmPassword,
                                        RedirectAttributes redirectAttributes){
        Optional<User> user1=userService.findUserByUserName(principal.getName());
        if(user1.isPresent()) {
            User existingUser = user1.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            if (profilePic.isEmpty()) {
                String fileName = StringUtils.cleanPath(profilePic.getOriginalFilename());
                String uploadPic = "images/" + existingUser.getId();
                FileUploadUtil.saveFile(uploadPic, fileName, profilePic);
            }
            userService.saveUser(existingUser);

            if (oldPassword != null && !oldPassword.isEmpty()) {
                if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
                    redirectAttributes.addFlashAttribute("error", "Old password is incorrect!");
                    return "redirect:/admin/settings";
                }
                if (!newPassword.equals(confirmPassword)) {
                    redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
                    return "redirect:/admin/settings";
                }
                existingUser.setPassword(passwordEncoder.encode(newPassword));
            }
        }
        return "admin/settings";
    }


}
