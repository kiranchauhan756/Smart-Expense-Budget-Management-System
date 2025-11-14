package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.CategoryService;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public AdminDashboardController(UserService userService,CategoryService categoryService) {
        this.userService = userService;
        this.categoryService=categoryService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboardPage(Model model, Principal principal){
       String username=principal.getName();
       Optional<User> user=userService.findUserByUserName(username);
       model.addAttribute("user",user);
        return "admin/dashboard";
    }
    @GetMapping("/users")
    public String showAdminUsersPage(Model model){
        Integer totalUsers= userService.getTotalUsers();
        Integer totalCategories= categoryService.getTotalCategories();
        model.addAttribute("totalUsers",totalUsers);
        model.addAttribute("totalCategories",totalCategories);
        return "admin/users";
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
    public String showAdminHomePage(Model model){
        Integer totalUsers= userService.getTotalUsers();
        Integer totalCategories= categoryService.getTotalCategories();
        model.addAttribute("totalUsers",totalUsers);
        model.addAttribute("totalCategories",totalCategories);
        return "admin/home";
    }
    @GetMapping("/audit")
    public String showAdminAuditPage(){
        return "admin/audit";
    }

}
