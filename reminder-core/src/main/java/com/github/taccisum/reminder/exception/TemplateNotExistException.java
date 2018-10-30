package com.github.taccisum.reminder.exception;

/**
 * @author tac
 * @since 2018/10/30
 */
public class TemplateNotExistException extends ReminderException {
    public TemplateNotExistException(String code) {
        super(code);
    }
}
