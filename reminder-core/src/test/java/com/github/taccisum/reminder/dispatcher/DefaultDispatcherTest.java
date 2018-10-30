package com.github.taccisum.reminder.dispatcher;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.api.Sender;
import com.github.taccisum.reminder.api.Target;
import com.github.taccisum.reminder.api.TargetSelector;
import com.github.taccisum.reminder.exception.BuildMessageException;
import com.github.taccisum.reminder.exception.DispatcherException;
import com.github.taccisum.reminder.exception.ExceedMaxFailureSendTimesException;
import com.github.taccisum.reminder.exception.SendMessageException;
import com.github.taccisum.reminder.message.SimpleMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author tac
 * @since 2018/10/30
 */
public class DefaultDispatcherTest {
    private DefaultDispatcher dispatcher;
    private TargetSelector targetSelector;
    private MessageBuilder messageBuilder;
    private Sender sender;

    @Before
    public void setUp() throws Exception {
        dispatcher = spy(new DefaultDispatcher());
        targetSelector = mock(TargetSelector.class);
        doReturn(targetSelector).when(dispatcher).getTargetSelector("FOO");
        messageBuilder = mock(MessageBuilder.class);
        doReturn(messageBuilder).when(dispatcher).getMessageBuilder("FOO");
        sender = mock(Sender.class);
        dispatcher.setSender(sender);
    }

    @Test
    public void dispatch() throws Exception {
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            targets.add(new TestTarget(i));
        }
        when(targetSelector.select(any())).thenReturn(targets);
        when(messageBuilder.build(any(), any(), any())).thenReturn(new SimpleMessage());
        doNothing().when(sender).send(any(), any(), any(), any());

        assertThat(dispatcher.dispatch("FOO", null, null, null)).isEqualTo(100);
    }

    @Test
    public void dispatchWhenBuildMessageError() throws Exception {
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < DefaultDispatcher.MAX_FAIL_COUNT; i++) {
            targets.add(new TestTarget(i));
        }
        when(targetSelector.select(any())).thenReturn(targets);
        when(messageBuilder.build(any(), any(), any())).thenThrow(new BuildMessageException());

        assertThat(dispatcher.dispatch("FOO", null, null, null)).isEqualTo(0);
    }

    @Test
    public void dispatchWhenSendMessageError() throws Exception {
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < DefaultDispatcher.MAX_FAIL_COUNT; i++) {
            targets.add(new TestTarget(i));
        }
        when(targetSelector.select(any())).thenReturn(targets);
        when(messageBuilder.build(any(), any(), any())).thenReturn(new SimpleMessage());
        doThrow(new SendMessageException()).when(sender).send(any(), any(), any(), any());

        assertThat(dispatcher.dispatch("FOO", null, null, null)).isEqualTo(0);
    }

    @Test(expected = ExceedMaxFailureSendTimesException.class)
    public void dispatchWhenErrorCountGreaterThanMax() throws Exception {
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < DefaultDispatcher.MAX_FAIL_COUNT + 1; i++) {
            targets.add(new TestTarget(i));
        }
        when(targetSelector.select(any())).thenReturn(targets);
        when(messageBuilder.build(any(), any(), any())).thenThrow(new BuildMessageException());
        doThrow(new SendMessageException()).when(sender).send(any(), any(), any(), any());

        dispatcher.dispatch("FOO", null, null, null);
    }

    @Test(expected = DispatcherException.class)
    public void dispatchWhenUnknownError() throws Exception {
        ArrayList<Target> targets = new ArrayList<>();
        targets.add(new TestTarget(1));
        when(targetSelector.select(any())).thenReturn(targets);
        when(messageBuilder.build(any(), any(), any())).thenThrow(new RuntimeException());

        dispatcher.dispatch("FOO", null, null, null);
    }

    private static class TestTarget implements Target {
        private Integer id;

        public TestTarget(Integer id) {
            this.id = id;
        }

        @Override
        public Integer getId() {
            return id;
        }
    }
}