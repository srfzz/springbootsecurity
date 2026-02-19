package com.posts.demo.advices;

import com.posts.demo.config.ApiResponse;
import com.posts.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
         ApiResponse<?> apiResponse=ApiResponse.builder().success(false).status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                 .message(e.getMessage()).
                 data(null).
                 timestamp(LocalDateTime.now())
                 .build();
         return  new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ApiResponse<?> apiResponse=ApiResponse.builder().success(false).status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(e.getMessage()).
                data(null).
                timestamp(LocalDateTime.now())
                .build();
        return  new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors=e.getBindingResult().getFieldErrors().
                stream().
                collect(Collectors.toMap(FieldError::getField,
                error->error.getDefaultMessage()!=null ? error.getDefaultMessage():"Invalid Input",
                        (existingValue,newValue)->existingValue

                ));
        ApiResponse<?> apiResponse =ApiResponse.builder().success(false).status(String.valueOf(HttpStatus.BAD_REQUEST.value())).message("validation Errors").timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(jakarta.validation.ConstraintViolationException e) {

        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        });

        ApiResponse<Map<String, String>> apiResponse = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("Database Validation Errors")
                .data(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingBody(HttpMessageNotReadableException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(false)
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("Required request body is missing or invalid")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
