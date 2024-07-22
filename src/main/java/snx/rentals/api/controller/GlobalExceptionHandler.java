package snx.rentals.api.controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new LinkedHashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errors.put("errors", fieldErrors);

        return errors;
    }

    @ExceptionHandler({EntityNotFoundException.class, DataRetrievalFailureException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, List<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return produceMessages(ex);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, List<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return produceMessages(ex);
    }


    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, List<String>> handleInternalServerErrorException(HttpServerErrorException ex) {
        return produceMessages(ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Map<String, List<String>> handleBadCredentialsException(BadCredentialsException ex) {
        return produceMessages(ex);
    }

    private static Map<String, List<String>> produceMessages(Throwable ex) {
        List<String> messages = new LinkedList<>();
        while (ex != null && ex.getMessage() != null) {
            messages.add(ex.getMessage());
            ex = ex.getCause();
        }
        return Collections.singletonMap("messages", messages);
    }
}
