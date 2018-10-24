package com.github.taccisum.reminder.channel;

import com.github.taccisum.reminder.api.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class ChannelFactory {
    private static Map<String, Channel> channels = new HashMap<>();

    public static void put(String code, Channel channel) {
        channels.put(code, channel);
    }

    public static Channel create(String code) {
        return channels.get(code);
    }
}
