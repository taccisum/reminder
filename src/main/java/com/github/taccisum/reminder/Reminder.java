package com.github.taccisum.reminder;

import com.github.taccisum.reminder.api.ChannelDescriptor;
import com.github.taccisum.reminder.api.Dispatcher;
import com.github.taccisum.reminder.api.Subject;
import com.github.taccisum.reminder.dispatcher.DefaultDispatcher;

/**
 * @author tac
 * @since 24/10/2018
 */
public class Reminder extends AbstractReminder {
    private Dispatcher dispatcher;

    public Reminder() {
        dispatcher = new DefaultDispatcher();
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public int remind(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, Object... args) {
        return dispatcher.dispatch(remindCode, subject, channelDescriptor, args);
    }
}
