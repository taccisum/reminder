package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface Channel extends Unique {
    String name();

    void send(Target target, Message message, Object[] args);
}
