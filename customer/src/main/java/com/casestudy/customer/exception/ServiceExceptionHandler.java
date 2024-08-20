package com.casestudy.customer.exception;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private HashMap<Object, Object> response = new HashMap<>();

    // Handle CustomerNotFoundException
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        response.clear();
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle DuplicateCustomerException
    @ExceptionHandler(DuplicateCustomerFoundException.class)
    public ResponseEntity<Object> handleDuplicateCustomerException(DuplicateCustomerFoundException ex) {
        response.clear();
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        response.clear();
        HashMap<Object, Object> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        response.put("message", "Validation failed");
        response.put("status", status);
        response.put("errors", fieldErrors.stream()
                .map(fe -> errors.put(fe.getField(),fe.getDefaultMessage()))
                .toList());
        return new ResponseEntity<>(response, status);
    }
}
