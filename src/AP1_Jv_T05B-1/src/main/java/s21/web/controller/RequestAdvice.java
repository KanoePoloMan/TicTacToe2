package s21.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.security.auth.message.AuthException;
import s21.domain.exception.AIGameEndException;
import s21.domain.exception.GameEndException;
import s21.domain.exception.InvalidAIFieldException;
import s21.domain.exception.InvalidFieldException;

@ControllerAdvice
public class RequestAdvice {
    @ExceptionHandler(GameEndException.class)
    public ResponseEntity<String> handleGameEnd(GameEndException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<String> handleInvalidField(InvalidFieldException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(AIGameEndException.class)
    public ResponseEntity<String> handleAIGameEnd(AIGameEndException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(InvalidAIFieldException.class)
    public ResponseEntity<String> handleInvalidAIField(InvalidAIFieldException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException e) {
        return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuth(AuthException e) {
        return new ResponseEntity<>("Invalid authentication: " + e, HttpStatus.UNAUTHORIZED);
    }
}
