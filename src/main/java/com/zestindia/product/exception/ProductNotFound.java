package com.zestindia.product.exception;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message) {
        super(message);
    }
}
