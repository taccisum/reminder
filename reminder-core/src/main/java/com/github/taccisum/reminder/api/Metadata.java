package com.github.taccisum.reminder.api;

import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface Metadata {
    int init();

    int reload();

    Map<String, MessageTemplate> templates();

    MessageTemplate template(String code);
}
