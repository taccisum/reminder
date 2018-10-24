package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface Sender {
    void send(Target target, Message message, ChannelDescriptor channelDescriptor, Object... args);
}
