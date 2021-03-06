package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface MessageBuilder extends Unique {
    Message build(Target target, Subject subject, Object... args);
}
