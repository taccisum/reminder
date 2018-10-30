package com.github.taccisum.reminder.channel;

import com.github.taccisum.reminder.api.Channel;
import com.github.taccisum.reminder.exception.ChannelFactoryException;
import com.github.taccisum.reminder.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class ChannelFactory {
    private static Map<String, Channel> channels = new HashMap<>();

    public static void put(Channel channel) {
        MapUtils.putOrThrow(channels, channel.code(), channel, new ChannelFactoryException(String.format("channel \"%s\" is existed.", channel.code())));
    }

    public static Channel create(String code) throws ChannelFactoryException {
        return MapUtils.getOrThrow(channels, code, new ChannelFactoryException(String.format("channel \"%s\" does not exist.", code)));
    }

    public static void clear() {
        channels.clear();
    }
}
