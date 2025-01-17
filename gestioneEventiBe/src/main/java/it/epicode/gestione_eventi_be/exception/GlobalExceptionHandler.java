package it.epicode.gestione_eventi_be.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> entityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = EntityExistsException.class)
    protected ResponseEntity<Object> entityExists(EntityExistsException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> httpMessageNotReadable(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = SecurityException.class)
    protected ResponseEntity<Object> securityException(SecurityException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = AlreadyExistsException.class)
    protected ResponseEntity<Object> alreadyExists(AlreadyExistsException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.CONFLICT);
    } @ExceptionHandler(value = EmailAlreadyUsedException.class)
    protected ResponseEntity<Object> emailAlreadyExists(EmailAlreadyUsedException ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NotSameOrganizerEvent.class)
    protected ResponseEntity<Object> notSameOrganizer(NotSameOrganizerEvent ex) {
        return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = IdException.class)
    protected ResponseEntity<String> idExceptionHandler(IdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            errors.put(fieldName, violation.getMessage());

        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
