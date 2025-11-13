package com.smart_expense.budget_management_system.exception;

public class CategoryAlreadyExistsException extends RuntimeException{
    private String message;

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
