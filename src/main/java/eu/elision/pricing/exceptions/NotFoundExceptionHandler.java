package eu.elision.pricing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler for {@link NotFoundException}.
 * This class is used to handle exceptions thrown by the application.
 * It is used to return a response entity with the appropriate status code.
 * <p>
 * When a {@link NotFoundException} is thrown, the status code is set to 404.
 * </p>
 */
@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
