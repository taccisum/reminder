package com.github.taccisum.reminder.channel;

import com.github.taccisum.reminder.api.Channel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2018/10/30
 */
public class ChannelFallbackProxyTest {
    @Test
    public void testSimply() throws Exception {
        Channel delegate = mock(Channel.class);
        Channel fallback = mock(Channel.class);
        when(delegate.code()).thenReturn("delegate");
        when(delegate.name()).thenReturn("delegate channel");
        ChannelFallbackProxy proxy = new ChannelFallbackProxy(delegate, fallback);

        assertThat(proxy.code()).isEqualTo("delegate");
        assertThat(proxy.name()).isEqualTo("delegate channel");
        assertThat(proxy.getFallback()).isEqualTo(fallback);
        proxy.send(any(), any(), any());
        verify(delegate, times(1)).send(any(), any(), any());
    }
}