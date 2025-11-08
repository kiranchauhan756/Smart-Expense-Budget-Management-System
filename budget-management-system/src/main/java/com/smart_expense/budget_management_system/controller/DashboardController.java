package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAdminDashboardPage(Model model, Principal principal){
       String username=principal.getName();
       User user=userService.findUserByUserName(username);
       model.addAttribute(user);
        return "html/adminDashboard";
    }
    @GetMapping("/user")
    public String showUserDashboardPage(){
        return "html/userDashboard";
    }
    @GetMapping("/category")
    public String showCategoryPage(){
        return "html/category";
    }
    @GetMapping("/budget")
    public String showBudgetPage(){
        return "html/budget";
    }
    @GetMapping("/expense")
    public String showExpensePage(){
        return "html/expense";
    }
}
