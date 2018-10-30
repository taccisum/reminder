package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.exception.MessageBuilderFactoryException;
import com.github.taccisum.reminder.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class MessageBuilderFactory {
    private static Map<String, MessageBuilder> builders = new HashMap<>();

    public static void put(MessageBuilder builder) {
        MapUtils.putOrThrow(builders, builder.code(), builder, new MessageBuilderFactoryException(String.format("message builder \"%s\" is existed", builder.code())));
    }

    public static MessageBuilder create(String remindCode) {
        return MapUtils.getOrThrow(builders, remindCode, new MessageBuilderFactoryException(String.format("message builder \"%s\" does not exist", remindCode)));
    }
}
