package com.chouette.rankingWeb.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> bindExceptionHandler(final ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("입력정보가 올바르지 않습니다.");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> bindExceptionHandler(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body("입력정보가 올바르지 않습니다.");
    }
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> invalidFormExceptionHandler(final InvalidFormatException e) {
        return ResponseEntity.badRequest().body("입력정보가 올바르지 않습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> runtimeExceptionHandler(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> fileExceptionHandler( final MultipartException e ) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CustomPageUnavailableException.class)
    public String pageAvailableException( final CustomPageUnavailableException e ) {
        return e.getMessage();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404( NoHandlerFoundException e ) {
        return "common/error";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> DuplicateKeyExceptionHandler( final DuplicateKeyException e ) {
        return ResponseEntity.badRequest().body("duplicate key");
    }
}
