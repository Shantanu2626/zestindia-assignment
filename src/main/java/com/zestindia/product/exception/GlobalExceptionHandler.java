package com.zestindia.product.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<?> productNotFound(ProductNotFound exception){
        return buildResponse(HttpStatus.NOT_FOUND , exception.getMessage());
    }


    public ResponseEntity<?> buildResponse(HttpStatus status , String message){
        Map<String , Object> response =  new HashMap<>();
        response.put("message", message);
        response.put("time" , LocalDateTime.now());
        response.put("status" , status);
        return new ResponseEntity<>(response , status);
    }
}
