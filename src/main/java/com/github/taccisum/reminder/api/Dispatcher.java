package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface Dispatcher {
    int dispatch(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, Object... args);
}
