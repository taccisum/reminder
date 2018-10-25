package com.github.taccisum.reminder.sender;

import com.github.taccisum.reminder.api.*;
import com.github.taccisum.reminder.exception.ChannelReceiveException;
import com.github.taccisum.reminder.exception.SendMessageException;

import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public class DefaultSender implements Sender {
    @Override
    public void send(Target target, Message message, ChannelDescriptor channelDescriptor, Object... args) {
        List<Channel> channels = channelDescriptor.toChannels();
        int successChannelCount = channels.size();

        for (Channel channel : channels) {
            try {
                sendViaChannel(channel, target, message, args);
            } catch (ChannelReceiveException e) {
                // TODO::
                // 说明channel及其fallback channel均处理失败了
                successChannelCount--;
            }
        }

        if (successChannelCount <= 0) {
            // TODO:: 添加提示信息
            throw new SendMessageException();
        }
    }

    private void sendViaChannel(Channel channel, Target target, Message message, Object[] args) {
        try {
            channel.receive(target, message, args);
        } catch (ChannelReceiveException e) {
            if (channel instanceof FallbackCapableChannel) {
                Channel fallback = ((FallbackCapableChannel) channel).getFallback();
                if (fallback != null) {
                    sendViaChannel(fallback, target, message, args);
                    return;
                }
            }
            throw e;
        }
    }
}
