package com.smart_expense.budget_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping("/admin")
    public String showAdminDashboardPage(){
        return "html/adminDashboard";
    }
    @GetMapping("/user")
    public String showUserDashboardPage(){
        return "html/userDashboard";
    }
}
