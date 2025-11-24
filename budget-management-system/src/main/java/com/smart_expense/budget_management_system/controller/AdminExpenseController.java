package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.entity.Expenses;
import com.smart_expense.budget_management_system.service.CategoryService;
import com.smart_expense.budget_management_system.service.ExpenseService;
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
@RequestMapping("/admin/expense")
public class AdminExpenseController {
     private final ExpenseService expenseService;
     private final CategoryService categoryService;
    @Autowired
    public AdminExpenseController(ExpenseService expenseService,CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService=categoryService;
    }
    @GetMapping("/addExpense")
    public String saveAdminExpensePage(Model model) {
        Expenses expenses=new Expenses();
        model.addAttribute(expenses);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/addExpense";
    }
    @PostMapping("/save")
    public String showAdminExpensePage(@ModelAttribute("expenses") Expenses expenses){
        expenseService.saveExpense(expenses);
        return "redirect:/admin/expense";
    }
    @GetMapping
    public String showAdminAllExpenses(Model model){
        List<Expenses> expenseList=expenseService.getAllExpense();
        model.addAttribute("expenses",expenseList);
        return "admin/expense";
    }
    @GetMapping("/edit")
    public String showAdminExpenseUpdatePage(@RequestParam("expenseId") long id, Model model){
        Optional<Expenses> expense=expenseService.findExpenseById(id);
        if(expense.isPresent()){
            model.addAttribute("expense",expense.get());
            return "admin/editExpense";
        }
        return "admin/expense";
    }
    @GetMapping("/delete")
    public String deleteExpense(@RequestParam("expenseId") long id,Model model){
        Optional<Expenses> expense=expenseService.findExpenseById(id);
        expenseService.deleteExpense(id);
        model.addAttribute("expenses",expense);
        return "redirect:/admin/expense";
    }
}
