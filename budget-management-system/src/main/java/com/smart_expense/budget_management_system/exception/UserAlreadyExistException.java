package com.smart_expense.budget_management_system.exception;

public class UserAlreadyExistException extends RuntimeException{
    private String message;

    public UserAlreadyExistException(String message){
        super(message);
    }
}
