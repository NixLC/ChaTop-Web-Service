package snx.rentals.api.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new LinkedHashMap<>();
    Map<String, String> fieldErrors = new LinkedHashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      if(error.contains(TypeMismatchException.class)){
        errorMessage = "'" + ((FieldError) error).getRejectedValue() + "' is an invalid type for field '" + fieldName +"'";
      }
      fieldErrors.put(fieldName, errorMessage);
    });

    errors.put("timestamp", LocalDateTime.now());
    errors.put("errors", fieldErrors);

    return errors;
  }

  @ExceptionHandler({EntityNotFoundException.class, DataRetrievalFailureException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public Map<String, Object> handleEntityNotFoundException(Exception ex) {
    return produceMessages(ex);
  }

  @ExceptionHandler
  @ResponseBody
  public ResponseEntity<Map<String, Object>> handleDataAccessException(DataAccessException ex) {
    return switch (ex.getClass().getSimpleName()) {
      case "DataIntegrityViolationException" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(produceMessages(ex));
      default -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(produceMessages(ex));
    };
  }

  @ExceptionHandler
  @ResponseBody
  public ResponseEntity<Map<String, Object>> handleHttpServerErrorException(HttpServerErrorException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(produceMessages(ex));
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public Map<String, Object> handleBadCredentialsException(BadCredentialsException ex) {
    return produceMessages(ex);
  }

  // Handle all non-explicit exception that could happen
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Map<String, Object> handleGenericException(Exception ex) {
    return produceMessages(ex);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, Object> errors = new LinkedHashMap<>();
    Map<String, List<String>> fieldErrors = new LinkedHashMap<>();
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

    for (ConstraintViolation<?> violation : violations) {
      String propertyName = getLastNode(violation.getPropertyPath());
      fieldErrors.putIfAbsent(propertyName, new LinkedList<>());
      fieldErrors.get(propertyName).add(violation.getMessage());
    }
    errors.put("timestamp", LocalDateTime.now());
    errors.put("errors", fieldErrors);
    return errors;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Map<String, Object> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
    return produceMessages(ex);
  }

  private static Map<String, Object> produceMessages(Throwable ex) {
    Map<String, Object> error = new LinkedHashMap<>();
    List<String> messages = new LinkedList<>();

    while (ex != null && ex.getMessage() != null) {
      messages.add(ex.getMessage());
      ex = ex.getCause();
    }
    error.put("timestamp", LocalDateTime.now());
    error.put("errors", messages);

    return error;
  }

  private String getLastNode(Path propertyPath) {
    String propertyName = null;
    for (Path.Node node : propertyPath) {
      propertyName = node.getName();
    }
    return propertyName;
  }
}
