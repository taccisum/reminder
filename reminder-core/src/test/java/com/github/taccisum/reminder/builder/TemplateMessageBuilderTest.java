package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.Message;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.api.Subject;
import com.github.taccisum.reminder.api.Target;
import com.github.taccisum.reminder.message.SimpleMessageTemplate;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author tac
 * @since 2018/10/30
 */
public class TemplateMessageBuilderTest {
    @Test
    public void getTemplate() throws Exception {
        Metadata metadata = mock(Metadata.class);
        when(metadata.template("FOO")).thenReturn(new SimpleMessageTemplate("CODE", "TOPIC", "BODY"));
        FooTemplateMessageBuilder mb = new FooTemplateMessageBuilder(metadata);
        assertThat(mb.getTemplate().getCode()).isEqualTo("CODE");
        assertThat(mb.getTemplate().getTopic()).isEqualTo("TOPIC");
        assertThat(mb.getTemplate().getBody()).isEqualTo("BODY");
    }

    private static class FooTemplateMessageBuilder extends TemplateMessageBuilder {
        public FooTemplateMessageBuilder(Metadata metadata) {
            super(metadata);
        }

        @Override
        public Message build(Target target, Subject subject, Object... args) {
            return null;
        }

        @Override
        public String code() {
            return "FOO";
        }
    }
}