package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin/settings")
public class AdminSettingsController {
   private final UserService userService;

    @Autowired
    public AdminSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminSettingsPage(Principal principal, Model model){
         Optional<User> user=userService.findUserByUserName(principal.getName());
        user.ifPresent(value -> model.addAttribute("user", value));
        return "admin/settings";
    }
}
