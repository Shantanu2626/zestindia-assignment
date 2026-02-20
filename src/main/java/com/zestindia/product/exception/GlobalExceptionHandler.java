package com.zestindia.product.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(UserConflictException.class)
    public ResponseEntity<?> productNotFound(UserConflictException exception){
        return buildResponse(HttpStatus.CONFLICT , exception.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> productNotFound(UserNotFoundException exception){
        return buildResponse(HttpStatus.NOT_FOUND , exception.getMessage());
    }

    @ExceptionHandler(PasswordIsIncorrect.class)
    public ResponseEntity<?> productNotFound(PasswordIsIncorrect exception){
        return buildResponse(HttpStatus.BAD_REQUEST , exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation Failed");
        response.put("errors", errors);
        response.put("time", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> buildResponse(HttpStatus status , String message){
        Map<String , Object> response =  new HashMap<>();
        response.put("message", message);
        response.put("time" , LocalDateTime.now());
        response.put("status" , status);
        return new ResponseEntity<>(response , status);
    }
}
