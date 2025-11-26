package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.Role;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/settings")
public class UserAdminSettingsController {
    private final UserService userService;

    @Autowired
    public UserAdminSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminSettings(Principal principal, Model model) {


        boolean isAdmin = false;
        model.addAttribute("user", new User());
        if (principal != null) {
            Optional<User> userOpt = userService.findUserByUserName(principal.getName());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("user", user);

                List<Role> roles = user.getRoles();
                isAdmin = roles.stream()
                        .anyMatch(r -> r.getName().equals("ADMIN"));

            } else {
                model.addAttribute("user", new User()); // fallback
            }
        }

        model.addAttribute("layout", isAdmin ? "admin/dashboard" : "user/dashboard");
        return "home/settings";
    }
    @PostMapping
    public String updateAdminSettings(
            Principal principal,
            @ModelAttribute("user") User formUser,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            @RequestParam(value = "action", required = false) String action,
            RedirectAttributes redirectAttributes) throws IOException {

        Optional<User> optUser = userService.findUserByUserName(principal.getName());

        if (optUser.isPresent()) {
            User dbUser = optUser.get();

            dbUser.setEmail(formUser.getEmail());
            dbUser.setPhoneNumber(formUser.getPhoneNumber());
            dbUser.setGender(formUser.getGender());
            dbUser.setDob(formUser.getDob());



                if ("save".equals(action) && newPassword != null && !newPassword.isEmpty()) {
                    if (!newPassword.equals(confirmPassword)) {
                        redirectAttributes.addFlashAttribute("error","Passwords are not matching !");
                        return "redirect:/home/settings";
                    }
                    redirectAttributes.addFlashAttribute("success", "Password changed successfully!");
                }

                // Handle profile picture upload
                else if ("upload".equals(action)) {
                    if (profilePic != null && !profilePic.isEmpty()) {
                        String filename = profilePic.getOriginalFilename();
                        Path uploadPath = Paths.get("src/main/resources/static/images/");
                        if (!Files.exists(uploadPath)) {
                            Files.createDirectories(uploadPath);
                        }
                        try (InputStream inputStream = profilePic.getInputStream()) {
                            assert filename != null;
                            Path filePath = uploadPath.resolve(filename);
                            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        }

                        dbUser.setProfilePicture(filename);
                        redirectAttributes.addFlashAttribute("success", "Profile picture updated successfully!");
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Please select a file to upload!");
                    }
                }

                // Handle profile picture deletion
                else if ("delete".equals(action)) {
                    dbUser.setProfilePicture(null);
                    redirectAttributes.addFlashAttribute("success", "Profile picture deleted successfully!");
                }

                // Handle general profile save without password/photo change
                else if ("save".equals(action) || action == null) {
                    redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
                }



            userService.updateUser(dbUser,newPassword,confirmPassword);
        }

        return "redirect:/home/settings";
    }

}