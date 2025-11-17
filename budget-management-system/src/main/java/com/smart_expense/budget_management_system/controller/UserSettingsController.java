package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserSettingsController {
    private final UserService userService;

    public UserSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String showUserSettingsPage(Principal principal, Model model){
        Optional<User> user=userService.findUserByUserName(principal.getName());
        user.ifPresent(value -> model.addAttribute("user", value));
        return "user/settings";
    }
}
