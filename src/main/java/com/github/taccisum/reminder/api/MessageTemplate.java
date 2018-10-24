package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface MessageTemplate {
    String getCode();
    String getTopic();
    String getBody();
}
