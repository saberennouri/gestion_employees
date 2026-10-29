package tn.saber.GestionEmployes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // ==========================================
  // Employee Not Found
  // ==========================================

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<Map<String, Object>>
  handleEmployeeNotFound(
    EmployeeNotFoundException ex) {

    Map<String, Object> response =
      new HashMap<>();

    response.put("status", 404);
    response.put("error", "Not Found");
    response.put("message", ex.getMessage());

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(response);
  }


  // ==========================================
  // Validation errors
  // ==========================================

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>>
  handleValidationErrors(
    MethodArgumentNotValidException ex) {

    Map<String, Object> errors =
      new HashMap<>();

    ex.getBindingResult()
      .getFieldErrors()
      .forEach(error ->
        errors.put(
          error.getField(),
          error.getDefaultMessage()
        )
      );

    Map<String, Object> response =
      new HashMap<>();

    response.put("status", 400);
    response.put("error", "Validation Error");
    response.put("errors", errors);

    return ResponseEntity
      .badRequest()
      .body(response);
  }
}
