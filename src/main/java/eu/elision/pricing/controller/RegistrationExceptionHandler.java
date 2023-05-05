package eu.elision.pricing.controller;

import eu.elision.pricing.exception.EmailAlreadyRegistered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions thrown by the registration process.
 *
 * @see AuthenticationController
 */
@ControllerAdvice
public class RegistrationExceptionHandler {

    /**
     * Handles the exception thrown when the user tries to register with an email
     * that is already registered.
     *
     * @param exception the exception thrown when the user tries to register with
     *                  an email that is already registered
     * @return the response entity containing the exception message and the HTTP status code 409
     */
    @ExceptionHandler(EmailAlreadyRegistered.class)
    public ResponseEntity<String> handleEmailAlreadyRegistered(EmailAlreadyRegistered exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
