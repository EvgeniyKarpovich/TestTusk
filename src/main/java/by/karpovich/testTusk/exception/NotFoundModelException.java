package by.karpovich.testTusk.exception;

public class NotFoundModelException extends RuntimeException {
    public NotFoundModelException() {
    }

    public NotFoundModelException(String message) {
        super(message);
    }
}