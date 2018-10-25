package com.github.taccisum.reminder.exception;

/**
 * @author tac
 * @since 24/10/2018
 */
public class RemindingException extends RuntimeException {
    public RemindingException() {
    }

    public RemindingException(String message) {
        super(message);
    }

    public RemindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemindingException(Throwable cause) {
        super(cause);
    }
}
