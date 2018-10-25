package com.github.taccisum.reminder.api;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface Dispatcher {
    default int dispatch(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, Object... args) {
        return dispatch(remindCode, subject, channelDescriptor, null, args);
    }

    int dispatch(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, TargetSelector targetSelector, Object... args);
}
