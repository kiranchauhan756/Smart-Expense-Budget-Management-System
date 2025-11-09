package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

  // This mapping is showing the home page
    @GetMapping("/")
    public String showHomePage(){
        return "home/homePage";
    }
    @RequestMapping("/login")
    public String loginPage(){
        return "home/login";
    }
    @GetMapping("/signUp")
    public String signUpPage(Model model){
        model.addAttribute("user",new User());
        return "home/signUp";
    }
}
