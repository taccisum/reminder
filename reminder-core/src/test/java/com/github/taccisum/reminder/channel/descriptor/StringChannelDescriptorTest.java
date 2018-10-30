package com.github.taccisum.reminder.channel.descriptor;

import com.github.taccisum.reminder.api.Channel;
import com.github.taccisum.reminder.api.FallbackCapableChannel;
import com.github.taccisum.reminder.api.Message;
import com.github.taccisum.reminder.api.Target;
import com.github.taccisum.reminder.channel.ChannelFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tac
 * @since 25/10/2018
 */
public class StringChannelDescriptorTest {

    @Before
    public void setUp() throws Exception {
        ChannelFactory.put(new FooChannel("A"));
        ChannelFactory.put(new FooChannel("B"));
        ChannelFactory.put(new FooChannel("C"));
    }

    @Test
    public void toChannels() throws Exception {
        List<Channel> channels = new StringChannelDescriptor("A@B,C").toChannels();
        assertThat(channels.get(0)).isInstanceOf(FallbackCapableChannel.class);
        assertThat(channels.get(0).code()).isEqualTo("A");
        assertThat(((FallbackCapableChannel) channels.get(0)).getFallback()).isNotInstanceOf(FallbackCapableChannel.class);
        assertThat(((FallbackCapableChannel) channels.get(0)).getFallback().code()).isEqualTo("B");
        assertThat(channels.get(1)).isNotInstanceOf(FallbackCapableChannel.class);
        assertThat(channels.get(1).code()).isEqualTo("C");
    }

    @Test
    public void toS() throws Exception {
        assertThat(new StringChannelDescriptor("A_B, C")).isEqualTo("A_B, C");
    }

    static class FooChannel implements Channel {
        private String code;

        public FooChannel(String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public String name() {
            return code();
        }

        @Override
        public void send(Target target, Message message, Object[] args) {

        }
    }
}
