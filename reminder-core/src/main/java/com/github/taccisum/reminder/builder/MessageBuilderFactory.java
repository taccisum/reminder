package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class MessageBuilderFactory {
    private static Map<String, MessageBuilder> builders = new HashMap<>();

    public static void put(String remindCode, MessageBuilder builder) {
        builders.put(remindCode, builder);
    }

    public static MessageBuilder create(String remindCode) {
        return builders.get(remindCode);
    }
}
