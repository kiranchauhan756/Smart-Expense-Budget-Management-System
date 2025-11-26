package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.entity.Role;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.service.CategoryService;
import com.smart_expense.budget_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/category")
public class UserAdminCategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public UserAdminCategoryController(CategoryService categoryService,UserService userService) {
        this.categoryService = categoryService;
        this.userService=userService;
    }


    @PostMapping("/save")
    public String addCategory(@Valid @ModelAttribute("category") Category category){
            categoryService.saveCategory(category);



        return "redirect:/home/category";

    }

    @GetMapping("/addCategory")
    public String showAddCategoryPage(Model model){
        Category category=new Category();
        model.addAttribute(category);
        return"home/addCategory";
    }

    @GetMapping
    public String showAllCategories(Model model, Principal principal){
        List<Category> categoryList=categoryService.getAllCategories();
        model.addAttribute("categories",categoryList);
        boolean isAdmin = false;
        model.addAttribute("user", new User());
        if (principal != null) {
            Optional<User> userOpt = userService.findUserByUserName(principal.getName());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("user", user);

                List<Role> roles = user.getRoles();
                isAdmin = roles.stream()
                        .anyMatch(r -> r.getName().equals("ADMIN"));

            } else {
                model.addAttribute("user", new User());
            }
        }

        model.addAttribute("layout", isAdmin ? "admin/dashboard" : "user/dashboard");

        return "home/category";
    }
    @GetMapping("/edit")
    public String showAdminCategoryUpdatePage(@RequestParam("categoryId") long id, Model model){
        Optional<Category> category=categoryService.findCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "home/editCategory";
        }
        return "home/category";
    }
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("categoryId") long id,Model model){
        Optional<Category> category=categoryService.findCategoryById(id);
        categoryService.deleteCategory(id);
        model.addAttribute("categories",category);
        return "redirect:/home/category";
    }
}
