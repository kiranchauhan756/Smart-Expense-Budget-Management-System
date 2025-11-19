package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
     private final UserService userService;


    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showAdminUsersPage(Model model){
       List<User> userList=userService.getAllUsers();
       model.addAttribute("user",userList);
       return "admin/users";
    }

    @GetMapping("/saveUser")
    public String saveAdminUsers(@ModelAttribute("user") User user){
       userService.saveUser(user);
       return "admin/addUsers";
    }

    @GetMapping("/deleteUser")
    public String deleteAdminUser(@RequestParam("userId") long id,Model model){
       Optional<User> user=userService.findUserById(id);
       userService.deleteUser(id);
       model.addAttribute("user",user);
       return "redirect:/admin/users";
    }

}
