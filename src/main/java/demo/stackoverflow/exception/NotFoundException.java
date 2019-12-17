package demo.stackoverflow.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {
    private final HttpStatus errorStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
