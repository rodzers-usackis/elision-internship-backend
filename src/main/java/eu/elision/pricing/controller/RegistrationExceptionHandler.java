package eu.elision.pricing.controller;

import eu.elision.pricing.exception.EmailAlreadyRegistered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationExceptionHandler {

    @ExceptionHandler(EmailAlreadyRegistered.class)
    public ResponseEntity<String> handleEmailAlreadyRegistered(EmailAlreadyRegistered exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
