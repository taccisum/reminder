package com.github.taccisum.reminder;

import com.github.taccisum.reminder.channel.descriptor.StringChannelDescriptor;
import com.github.taccisum.reminder.api.ChannelDescriptor;
import com.github.taccisum.reminder.api.Subject;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class AbstractReminder {
    public abstract int remind(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, Object... args);

    public int remind(String remindCode, Object subject, ChannelDescriptor channelDescriptor, Object... args) {
        return remind(remindCode, subject::toString, channelDescriptor, args);
    }

    public int remind(String remindCode, Object subject, String channelDescriptor, Object... args) {
        return remind(remindCode, subject::toString, new StringChannelDescriptor(channelDescriptor), args);
    }

    public int remind(String remindCode, Subject subject, String channelDescriptor, Object... args) {
        return remind(remindCode, subject, new StringChannelDescriptor(channelDescriptor), args);
    }
}
