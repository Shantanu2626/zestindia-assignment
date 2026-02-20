package com.zestindia.product.exception;

public class PasswordIsIncorrect extends RuntimeException{
    public PasswordIsIncorrect(String message){
        super(message);
    }
}
