package com.smart_expense.budget_management_system.exception;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.entity.Expenses;
import com.smart_expense.budget_management_system.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model){
        model.addAttribute("user",new User());
        model.addAttribute("message",ex.getMessage());
     return "home/signUp";
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistException ex,Model model){
        model.addAttribute("user",new User());
        model.addAttribute("message",ex.getMessage());
        return "home/signUp";
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public String handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex,Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("message",ex.getMessage());
        return "admin/addCategory";
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleCategoryNotFoundException(CategoryNotFoundException ex,Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("message",ex.getMessage());
        return "admin/addCategory";
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public String handleExpenseNotFoundException(ExpenseNotFoundException ex,Model model){
        model.addAttribute("expense",new Expenses());
        model.addAttribute("message",ex.getMessage());
        return "admin/expense";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // Category Validation errors
        if (ex.getBindingResult().getTarget() instanceof Category) {
            model.addAttribute("category", new Category());
            model.addAttribute("errors", errors);
            return "admin/addCategory";
        }

        // Expense Validation errors
        if (ex.getBindingResult().getTarget() instanceof Expenses) {
            model.addAttribute("expense", new Expenses());
            model.addAttribute("errors", errors);
            return "admin/expense";
        }
        return "admin/expense";
    }

}
