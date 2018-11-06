package com.github.taccisum.reminder.spring;

import com.github.taccisum.reminder.builder.MessageBuilderFactory;
import com.github.taccisum.reminder.channel.ChannelFactory;
import com.github.taccisum.reminder.selector.TargetSelectorFactory;

/**
 * @author tac
 * @since 2018/11/6
 */
public class ReminderFactoryLifeCycleManager {
    public void destroy() {
        TargetSelectorFactory.clear();
        MessageBuilderFactory.clear();
        ChannelFactory.clear();
    }
}
