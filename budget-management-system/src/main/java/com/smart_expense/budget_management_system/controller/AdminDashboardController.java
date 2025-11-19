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
import java.util.List;
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

    @GetMapping("/budget")
    public String showAdminBudgetPage(){
        return "admin/budget";
    }
    @GetMapping("/expense")
    public String showAdminExpensePage(){
        return "admin/expense";
    }
    @GetMapping("/home")
    public String showAdminHomePage(Model model,Principal principal){
        String username=principal.getName();
        Optional<User> optionalUser=userService.findUserByUserName(username);
        User user = optionalUser.orElse(new User());
        List<User> listUsers=userService.getAllUsers();
        Integer totalUsers= userService.getTotalUsers();
        Integer totalCategories= categoryService.getTotalCategories();
        model.addAttribute("users",listUsers);
        model.addAttribute("user",user);
        model.addAttribute("totalUsers",totalUsers);
        model.addAttribute("totalCategories",totalCategories);
        return "admin/home";
    }
    @GetMapping("/audit")
    public String showAdminAuditPage(){
        return "admin/audit";
    }

}
