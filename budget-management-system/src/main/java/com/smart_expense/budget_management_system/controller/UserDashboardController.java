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
public class UserDashboardController {
    private final UserService userService;

    public UserDashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showUserDashboardPage(Model model, Principal principal){
       String username=principal.getName();
       Optional<User> user=userService.findUserByUserName(username);
        user.ifPresent(model::addAttribute);
        return "user/dashboard";
    }
    @GetMapping("/budget")
    public String showUserBudgetPage(){
        return "user/budget";
    }
    @GetMapping("/expense")
    public String showUserExpensePage(){
        return "user/expense";
    }
    @GetMapping("/home")
    public String showUserHomePage(){
        return "user/home";
    }

}
