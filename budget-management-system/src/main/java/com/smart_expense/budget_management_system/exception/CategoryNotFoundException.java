package com.smart_expense.budget_management_system.exception;

public class CategoryNotFoundException extends RuntimeException{
    private String message;

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
