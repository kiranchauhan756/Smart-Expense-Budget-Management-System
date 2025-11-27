package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.Expenses;
import com.smart_expense.budget_management_system.service.CategoryService;
import com.smart_expense.budget_management_system.service.UserExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user/expense")
public class UserExpenseController {
    private final UserExpenseService userExpenseService;
    private final CategoryService categoryService;

    public UserExpenseController(UserExpenseService userExpenseService, CategoryService categoryService) {
        this.userExpenseService = userExpenseService;
        this.categoryService = categoryService;
    }
    @GetMapping("/addExpense")
    public String saveUserExpensePage(Model model) {
        Expenses expenses=new Expenses();
        model.addAttribute(expenses);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user/addExpense";
    }
    @PostMapping("/save")
    public String showUserExpensePage(@Valid @ModelAttribute("expenses") Expenses expenses, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            Map<String,String> errors=new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error->errors.put(error.getField(),error.getDefaultMessage())
            );
            model.addAttribute("errors",errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "user/addExpense";
        }
        userExpenseService.saveExpense(expenses);
        return "redirect:/user/expense";
    }
    @GetMapping
    public String showUserAllExpenses(Model model){
        List<Expenses> expenseList=userExpenseService.getAllExpense();
        model.addAttribute("expenses",expenseList);
        return "user/expense";
    }

    @GetMapping("/edit")
    public String showUserExpenseUpdatePage(@RequestParam("expenseId") long id, Model model){
        Optional<Expenses> expense = userExpenseService.findExpenseById(id);
        if(expense.isPresent()){
            model.addAttribute("expenses", expense.get());
            model.addAttribute("categories", categoryService.getAllCategories()); // for dropdown
            return "user/editExpense";
        }
        return "redirect:/user/expense";
    }
    @GetMapping("/delete")
    public String deleteExpense(@RequestParam("expenseId") long id,Model model){
        Optional<Expenses> expense=userExpenseService.findExpenseById(id);
        userExpenseService.deleteExpense(id);
        model.addAttribute("expenses",expense);
        return "redirect:/user/expense";
    }
}
