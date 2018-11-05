package com.github.taccisum.reminder.sender;

import com.github.taccisum.reminder.api.*;
import com.github.taccisum.reminder.exception.ChannelSendException;
import com.github.taccisum.reminder.exception.SendMessageException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author tac
 * @since 25/10/2018
 */
public class DefaultSenderTest {
    DefaultSender sender;
    ChannelDescriptor descriptor;

    @Before
    public void setUp() throws Exception {
        sender = spy(new DefaultSender());
        descriptor = mock(ChannelDescriptor.class);
    }

    @Test
    public void sendWhenPartChannelException() throws Exception {
        ArrayList<Channel> channels = new ArrayList<>();
        Channel successChannel = mock(Channel.class);
        channels.add(null);
        channels.add(null);
        channels.add(successChannel);
        when(descriptor.toChannels()).thenReturn(channels);
        doThrow(ChannelSendException.class).when(sender).fallbackIfSendFailure((Channel) isNull(), any(), any(), any(), any());
        doNothing().when(sender).fallbackIfSendFailure(eq(successChannel), any(), any(), any(), any());
        sender.send(mock(Target.class), mock(Message.class), descriptor);
    }

    @Test(expected = SendMessageException.class)
    public void sendWhenAllChannelException() throws Exception {
        ArrayList<Channel> channels = new ArrayList<>();
        channels.add(null);
        channels.add(null);
        channels.add(null);
        when(descriptor.toChannels()).thenReturn(channels);
        doThrow(ChannelSendException.class).when(sender).fallbackIfSendFailure(any(), any(), any(), any(), any());
        sender.send(mock(Target.class), mock(Message.class), descriptor);
    }

    @Test
    public void fallbackIfSendFailureWhenChannelSuccess() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);
        doNothing().when(channelA).send(any(), any(), any());
        HashSet<String> sentChannels = new HashSet<>();
        sender.fallbackIfSendFailure(channelA, null, null, sentChannels, null);

        assertThat(sentChannels).contains("A");
        assertThat(sentChannels).doesNotContain("B");
        verify(channelA, times(1)).send(any(), any(), any());
        verify(channelB, times(0)).send(any(), any(), any());
    }

    @Test
    public void fallbackIfSendFailureWhenChannelIsSent() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);
        doNothing().when(channelA).send(any(), any(), any());
        HashSet<String> sentChannels = new HashSet<>();
        sentChannels.add(channelA.code());
        sender.fallbackIfSendFailure(channelA, null, null, sentChannels, null);

        assertThat(sentChannels).contains("A");
        assertThat(sentChannels).doesNotContain("B");
        verify(channelA, times(0)).send(any(), any(), any());
        verify(channelB, times(0)).send(any(), any(), any());
    }

    @Test
    public void fallbackIfSendFailureWhenChannelFailure() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);

        doThrow(ChannelSendException.class).when(channelA).send(any(), any(), any());
        doNothing().when(channelB).send(any(), any(), any());
        HashSet<String> sentChannels = new HashSet<>();
        sender.fallbackIfSendFailure(channelA, null, null, sentChannels, null);

        assertThat(sentChannels).doesNotContain("A");
        assertThat(sentChannels).contains("B");
        verify(channelA, times(1)).send(any(), any(), any());
        verify(channelB, times(1)).send(any(), any(), any());
    }

    @Test(expected = ChannelSendException.class)
    public void fallbackIfSendFailureWhenAllChannelFailure() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);

        doThrow(ChannelSendException.class).when(channelA).send(any(), any(), any());
        doThrow(ChannelSendException.class).when(channelB).send(any(), any(), any());
        sender.fallbackIfSendFailure(channelA, null, null, new HashSet<>(), null);
    }
}
