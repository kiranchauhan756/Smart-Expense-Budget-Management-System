package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

  // This mapping is showing the home page
    @RequestMapping("/")
    public String showHomePage(){
        return "html/homePage";
    }
    @RequestMapping("/login")
    public String loginPage(){
        return "html/login";
    }
    @RequestMapping("/budgetIQ")
    public String budgetIQPage(){
        return "html/budgetIQ";
    }
    @GetMapping("/signUp")
    public String signUpPage(Model model){
        model.addAttribute("user",new User());
        return "html/signUp";
    }
}
