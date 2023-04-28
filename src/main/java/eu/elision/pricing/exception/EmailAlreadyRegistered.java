package eu.elision.pricing.exception;

/**
 * Exception thrown when a user tries to register with an email that is already registered.
 */
public class EmailAlreadyRegistered extends RuntimeException {
    public EmailAlreadyRegistered(String message) {
        super(message);
    }
}
