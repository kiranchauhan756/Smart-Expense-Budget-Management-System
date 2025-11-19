package com.smart_expense.budget_management_system.exception;

public class PasswordException extends RuntimeException{
    private String message;

    public PasswordException(String message) {
        super(message);
    }
}
