package com.github.taccisum.reminder.exception;

/**
 * @author tac
 * @since 24/10/2018
 */
public class ReminderException extends RuntimeException {
    public ReminderException() {
    }

    public ReminderException(String message) {
        super(message);
    }

    public ReminderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReminderException(Throwable cause) {
        super(cause);
    }
}
