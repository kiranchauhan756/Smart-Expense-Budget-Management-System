package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.Expenses;
import com.smart_expense.budget_management_system.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

   @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    public void saveExpense(Expenses expenses){
       expenseRepository.save(expenses);
    }
}
