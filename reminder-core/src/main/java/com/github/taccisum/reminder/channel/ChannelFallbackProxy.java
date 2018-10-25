package com.github.taccisum.reminder.channel;

import com.github.taccisum.reminder.api.Channel;
import com.github.taccisum.reminder.api.FallbackCapableChannel;
import com.github.taccisum.reminder.api.Message;
import com.github.taccisum.reminder.api.Target;

/**
 * @author tac
 * @since 24/10/2018
 */
public class ChannelFallbackProxy implements FallbackCapableChannel {
    private Channel delegate;
    private Channel fallback;

    public ChannelFallbackProxy(Channel delegate, Channel fallback) {
        this.delegate = delegate;
        this.fallback = fallback;
    }

    @Override
    public String code() {
        return delegate.code();
    }

    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public void send(Target target, Message message, Object[] args) {
        delegate.send(target, message, args);
    }

    @Override
    public Channel getFallback() {
        return fallback;
    }
}
