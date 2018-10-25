package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface MessageTemplate extends Unique {
    String getCode();

    String getTopic();

    String getBody();

    @Override
    default String code() {
        return getCode();
    }
}
