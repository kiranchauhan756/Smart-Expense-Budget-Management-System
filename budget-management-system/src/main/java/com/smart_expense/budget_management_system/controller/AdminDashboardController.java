package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    private final UserService userService;

    @Autowired
    public AdminDashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboardPage(Model model, Principal principal){
       String username=principal.getName();
       User user=userService.findUserByUserName(username);
       model.addAttribute("user",user);
        return "admin/dashboard";
    }
    @GetMapping("/users")
    public String showAdminUsersPage(){
        return "admin/users";
    }
    @GetMapping("/category")
    public String showAdminCategoryPage(){
        return "admin/category";
    }
    @GetMapping("/budget")
    public String showAdminBudgetPage(){
        return "admin/budget";
    }
    @GetMapping("/expense")
    public String showAdminExpensePage(){
        return "admin/expense";
    }
    @GetMapping("/settings")
    public String showAdminSettingsPage(){
        return "admin/settings";
    }
    @GetMapping("/home")
    public String showAdminHomePage(){
        return "admin/home";
    }
    @GetMapping("/audit")
    public String showAdminAuditPage(){
        return "admin/audit";
    }
}
