package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin/settings")
public class AdminSettingsController {
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSettingsController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showAdminSettings(Principal principal, Model model) {
        Optional<User> user = userService.findUserByUserName(principal.getName());
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", new User());
        }
        return "admin/settings";
    }

    @PostMapping
    public String updateAdminSettings(
            Principal principal,
            @ModelAttribute("user") User user,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            RedirectAttributes redirectAttributes) throws IOException {

        User existingUser = userService.findUserByUserName(principal.getName()).get();
        user.setId(existingUser.getId());

        userService.updateUser(user, profilePic, oldPassword, newPassword, confirmPassword);

        redirectAttributes.addFlashAttribute("success", "Settings updated successfully!");
        return "redirect:/admin/settings";
    }

}