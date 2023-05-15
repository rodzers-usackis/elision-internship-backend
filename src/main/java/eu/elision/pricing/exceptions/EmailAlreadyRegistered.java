package eu.elision.pricing.exceptions;

/**
 * Exception thrown when a user tries to register with an email that is already registered.
 */
public class EmailAlreadyRegistered extends RuntimeException {
    public EmailAlreadyRegistered(String message) {
        super(message);
    }
}
