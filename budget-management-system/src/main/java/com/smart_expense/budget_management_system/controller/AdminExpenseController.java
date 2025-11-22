package com.smart_expense.budget_management_system.controller;

import com.smart_expense.budget_management_system.entity.Expenses;
import com.smart_expense.budget_management_system.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/expense")
public class AdminExpenseController {
     private final ExpenseService expenseService;
    @Autowired
    public AdminExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/addExpense")
    public String saveAdminExpensePage(@ModelAttribute("expenses") Expenses expenses){
        expenseService.saveExpense(expenses);
        return "redirect:/admin/expenses";
    }
}
