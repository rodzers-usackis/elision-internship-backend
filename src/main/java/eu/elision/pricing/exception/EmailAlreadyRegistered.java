package eu.elision.pricing.exception;

public class EmailAlreadyRegistered extends RuntimeException {
    public EmailAlreadyRegistered(String message) {
        super(message);
    }
}
