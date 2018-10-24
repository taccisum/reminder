package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface MessageBuilder {
    Message build(Target target, MessageTemplate template, Subject subject, Object... args);
}
