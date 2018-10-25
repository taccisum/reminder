package com.github.taccisum.reminder.sender;

import com.github.taccisum.reminder.api.*;
import com.github.taccisum.reminder.exception.ChannelReceiveException;
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
        doThrow(ChannelReceiveException.class).when(sender).fallbackIfReceiveFailure((Channel) isNull(), any(), any(), any(), any());
        doNothing().when(sender).fallbackIfReceiveFailure(eq(successChannel), any(), any(), any(), any());
        sender.send(mock(Target.class), mock(Message.class), descriptor);
    }

    @Test(expected = SendMessageException.class)
    public void sendWhenAllChannelException() throws Exception {
        ArrayList<Channel> channels = new ArrayList<>();
        channels.add(null);
        channels.add(null);
        channels.add(null);
        when(descriptor.toChannels()).thenReturn(channels);
        doThrow(ChannelReceiveException.class).when(sender).fallbackIfReceiveFailure(any(), any(), any(), any(), any());
        sender.send(mock(Target.class), mock(Message.class), descriptor);
    }

    @Test
    public void fallbackIfReceiveFailureWhenChannelSuccess() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);
        doNothing().when(channelA).receive(any(), any(), any());
        HashSet<String> sentChannels = new HashSet<>();
        sender.fallbackIfReceiveFailure(channelA, null, null, sentChannels, null);

        assertThat(sentChannels).contains("A");
        assertThat(sentChannels).doesNotContain("B");
        verify(channelA, times(1)).receive(any(), any(), any());
        verify(channelB, times(0)).receive(any(), any(), any());
    }

    @Test
    public void fallbackIfReceiveFailureWhenChannelIsSent() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);
        doNothing().when(channelA).receive(any(), any(), any());
        HashSet<String> sentChannels = new HashSet<>();
        sentChannels.add(channelA.code());
        sender.fallbackIfReceiveFailure(channelA, null, null, sentChannels, null);

        assertThat(sentChannels).contains("A");
        assertThat(sentChannels).doesNotContain("B");
        verify(channelA, times(0)).receive(any(), any(), any());
        verify(channelB, times(0)).receive(any(), any(), any());
    }

    @Test
    public void fallbackIfReceiveFailureWhenChannelFailure() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);

        doThrow(ChannelReceiveException.class).when(channelA).receive(any(), any(), any());
        doNothing().when(channelB).receive(any(), any(), any());
        HashSet<String> sentChannels = new HashSet<>();
        sender.fallbackIfReceiveFailure(channelA, null, null, sentChannels, null);

        assertThat(sentChannels).doesNotContain("A");
        assertThat(sentChannels).contains("B");
        verify(channelA, times(1)).receive(any(), any(), any());
        verify(channelB, times(1)).receive(any(), any(), any());
    }

    @Test(expected = ChannelReceiveException.class)
    public void fallbackIfReceiveFailureWhenAllChannelFailure() throws Exception {
        Channel channelB = mock(Channel.class);
        FallbackCapableChannel channelA = mock(FallbackCapableChannel.class);
        when(channelA.code()).thenReturn("A");
        when(channelB.code()).thenReturn("B");
        when(channelA.getFallback()).thenReturn(channelB);

        doThrow(ChannelReceiveException.class).when(channelA).receive(any(), any(), any());
        doThrow(ChannelReceiveException.class).when(channelB).receive(any(), any(), any());
        sender.fallbackIfReceiveFailure(channelA, null, null, new HashSet<>(), null);
    }
}
