package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/save")
    public String addCategory(@ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/category";

    }

    @GetMapping("/addCategory")
    public String showAddCategoryPage(Model model){
        Category category=new Category();
        model.addAttribute(category);
        return"admin/addCategory";
    }

    @GetMapping
    public String getAllCategories(Model model){
        List<Category> categoryList=categoryService.getAllCategories();
        model.addAttribute("categories",categoryList);
        return "admin/category";
    }

}
