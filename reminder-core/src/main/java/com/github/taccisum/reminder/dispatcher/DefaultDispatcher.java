package com.github.taccisum.reminder.dispatcher;

import com.github.taccisum.reminder.api.*;
import com.github.taccisum.reminder.builder.MessageBuilderFactory;
import com.github.taccisum.reminder.exception.BuildMessageException;
import com.github.taccisum.reminder.exception.DispatcherException;
import com.github.taccisum.reminder.exception.ExceedMaxFailureSendTimesException;
import com.github.taccisum.reminder.exception.SendMessageException;
import com.github.taccisum.reminder.selector.TargetSelectorFactory;
import com.github.taccisum.reminder.sender.DefaultSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public class DefaultDispatcher implements Dispatcher {
    public static final int MAX_FAIL_COUNT = 1000;
    private Sender sender;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DefaultDispatcher() {
        this.sender = new DefaultSender();
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public int dispatch(String remindCode, Subject subject, ChannelDescriptor channelDescriptor, Object... args) {
        TargetSelector targetSelector = getTargetSelector(remindCode);
        MessageBuilder messageBuilder = getMessageBuilder(remindCode);
        int failCount = 0;

        List<Target> targets = targetSelector.select(args);
        for (Target target : targets) {
            try {
                // try块执行，防止单条信息发送失败导致所有提醒失败
                Message message = messageBuilder.build(target, subject, args);
                sender.send(target, message, channelDescriptor, args);
            } catch (BuildMessageException e) {
                logger.error("build message - [{}] failure. target: {}", remindCode, target);
                failCount++;
            } catch (SendMessageException e) {
                logger.error("send message - [{}] failure. target: {}", remindCode, target);
                failCount++;
            } catch (Exception e) {
                throw new DispatcherException(e);
            } finally {
                if (failCount > MAX_FAIL_COUNT) {
                    // 限制最大失败次数，防止一直失败大量消耗系统资源
                    throw new ExceedMaxFailureSendTimesException(MAX_FAIL_COUNT);
                }
            }
        }
        return targets.size() - failCount;
    }

    MessageBuilder getMessageBuilder(String remindCode) {
        return MessageBuilderFactory.create(remindCode);
    }

    TargetSelector getTargetSelector(String remindCode) {
        return TargetSelectorFactory.create(remindCode);
    }
}
