package com.smart_expense.budget_management_system.exception;

public class ExpenseNotFoundException extends RuntimeException{
    private String message;

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
