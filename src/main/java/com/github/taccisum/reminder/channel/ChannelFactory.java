package com.github.taccisum.reminder.channel;

import com.github.taccisum.reminder.api.Channel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class ChannelFactory {
    public static Channel create(String code) {
        throw new NotImplementedException();
    }
}
