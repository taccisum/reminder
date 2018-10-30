package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.exception.MessageBuilderNotExistException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class MessageBuilderFactory {
    private static Map<String, MessageBuilder> builders = new HashMap<>();

    public static void put(MessageBuilder builder) {
        builders.put(builder.code(), builder);
    }

    public static MessageBuilder create(String remindCode) {
        if (builders.containsKey(remindCode)) {
            return builders.get(remindCode);
        }
        throw new MessageBuilderNotExistException(remindCode);
    }
}
