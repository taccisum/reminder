package com.github.taccisum.reminder.dispatcher;

import com.github.taccisum.reminder.api.*;
import com.github.taccisum.reminder.builder.MessageBuilderFactory;
import com.github.taccisum.reminder.exception.BuildMessageException;
import com.github.taccisum.reminder.exception.SendMessageException;
import com.github.taccisum.reminder.selector.TargetSelectorFactory;
import com.github.taccisum.reminder.sender.DefaultSender;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public class DefaultDispatcher implements Dispatcher {
    public static final int MAX_FAIL_COUNT = 1000;
    private Sender sender;

    public DefaultDispatcher() {
        this.sender = new DefaultSender();
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public int dispatch(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, Object... args) {
        TargetSelector targetSelector = TargetSelectorFactory.create(remindCode);
        MessageBuilder messageBuilder = MessageBuilderFactory.create(remindCode);
        int failCount = 0;

        List<Target> targets = targetSelector.select(args);
        for (Target target : targets) {
            try {
                // try块执行，防止单条信息发送失败导致所有提醒失败
                Message message = messageBuilder.build(target, subject, args);
                sender.send(target, message, channelDescriptor, args);
            } catch (BuildMessageException e) {
                throw new NotImplementedException();
            } catch (SendMessageException e) {
                throw new NotImplementedException();
            } catch (Exception e) {
                if (failCount > MAX_FAIL_COUNT) {
                    // 限制最大失败次数，防止一直失败大量消耗系统资源
                    // TODO::
                    throw new RuntimeException();
                }
                failCount++;
            }
        }
        return targets.size() - failCount;
    }
}
