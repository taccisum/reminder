package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TemaplteMessageBuilder implements MessageBuilder {
    protected Metadata metadata;

    public TemaplteMessageBuilder(Metadata metadata) {
        this.metadata = metadata;
    }

    protected MessageTemplate getTemplate() {
        return metadata.templates().get(code());
    }
}
