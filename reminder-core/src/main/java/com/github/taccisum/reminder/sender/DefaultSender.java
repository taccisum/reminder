package com.github.taccisum.reminder.sender;

import com.github.taccisum.reminder.api.*;
import com.github.taccisum.reminder.exception.ChannelSendException;
import com.github.taccisum.reminder.exception.SendMessageException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tac
 * @since 24/10/2018
 */
public class DefaultSender implements Sender {
    @Override
    public void send(Target target, Message message, ChannelDescriptor channelDescriptor, Object... args) {
        List<Channel> channels = channelDescriptor.toChannels();
        Set<String> sentChannels = new HashSet<>();
        int successChannelCount = channels.size();

        for (Channel channel : channels) {
            try {
                fallbackIfSendFailure(channel, target, message, sentChannels, args);
            } catch (ChannelSendException e) {
                // 说明channel及其fallback channel均处理失败了
                successChannelCount--;
            }
        }

        if (successChannelCount <= 0) {
            throw new SendMessageException();
        }
    }

    void fallbackIfSendFailure(Channel channel, Target target, Message message, Set<String> sentChannels, Object[] args) {
        try {
            if (sentChannels.contains(channel.code())) {
                // 避免往同一个channel重新发送数据
                return;
            }
            channel.send(target, message, args);
            sentChannels.add(channel.code());
        } catch (ChannelSendException e) {
            if (channel instanceof FallbackCapableChannel) {
                Channel fallback = ((FallbackCapableChannel) channel).getFallback();
                if (fallback != null) {
                    fallbackIfSendFailure(fallback, target, message, sentChannels, args);
                    return;
                }
            }
            throw e;
        }
    }
}
