package com.github.taccisum.reminder.exception;

/**
 * @author tac
 * @since 25/10/2018
 */
public class ExceedMaxFailureSendTimesException extends DispatchException {
    public ExceedMaxFailureSendTimesException(int max) {
        super("max: " + max);
    }
}
