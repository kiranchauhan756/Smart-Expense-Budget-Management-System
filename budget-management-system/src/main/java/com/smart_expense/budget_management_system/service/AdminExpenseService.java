package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.Expenses;
import com.smart_expense.budget_management_system.exception.ExpenseNotFoundException;
import com.smart_expense.budget_management_system.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminExpenseService {
    private final ExpenseRepository expenseRepository;

   @Autowired
    public AdminExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    public void saveExpense(Expenses expenses){
       expenseRepository.save(expenses);
    }

    public List<Expenses> getAllExpense() {
       return expenseRepository.findAll();
    }

    public Optional<Expenses> findExpenseById(long id) {
            Optional<Expenses> optionalExpenses=expenseRepository.findById(id);
            if(optionalExpenses.isEmpty())throw new ExpenseNotFoundException("expense not found !!");
            return optionalExpenses;
    }

    public void deleteExpense(long id) {
        Optional<Expenses> expense=expenseRepository.findById(id);
        if(expense.isEmpty())throw new ExpenseNotFoundException("expense not found !!");
        else{
            expenseRepository.deleteById(id);
        }
    }
}
