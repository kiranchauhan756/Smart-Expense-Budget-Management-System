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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
    public String showAllCategories(Model model){
        List<Category> categoryList=categoryService.getAllCategories();
        model.addAttribute("categories",categoryList);
        return "admin/category";
    }
    @GetMapping("/edit")
    public String showAdminCategoryUpdatePage(@RequestParam("categoryId") long id,Model model){
        Optional<Category> category=categoryService.findCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "admin/editCategory";
        }
        return "admin/category";
    }
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("categoryId") long id,Model model){
        Optional<Category> category=categoryService.findCategoryById(id);
        categoryService.deleteCategory(id);
        model.addAttribute("categories",category);
        return "redirect:/admin/category";
    }
}
