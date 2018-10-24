package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class MessageBuilderFactory {
    public static MessageBuilder create(String remindCode) {
        throw new NotImplementedException();
    }
}
