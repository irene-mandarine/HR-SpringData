package com.webApp.HRdatabase.exceptionHandler;

import com.webApp.HRdatabase.exceptions.DepartmentNotFoundException;
import com.webApp.HRdatabase.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(RuntimeException e, String message, WebRequest request) {
        return handleExceptionInternal(e, message, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Object> handleEmployeeNotFoundException(RuntimeException e, String message, WebRequest request) {
        return handleExceptionInternal(e, message, new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<Object> handleDepartmentNotFoundException(RuntimeException e, String message, WebRequest request) {
        return handleExceptionInternal(e, message, new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }
}
