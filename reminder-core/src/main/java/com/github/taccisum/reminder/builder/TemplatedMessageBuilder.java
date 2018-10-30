package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.exception.TemplateNotExistException;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TemplatedMessageBuilder implements MessageBuilder {
    protected Metadata metadata;

    public TemplatedMessageBuilder(Metadata metadata) {
        this.metadata = metadata;
    }

    protected MessageTemplate getTemplate() throws TemplateNotExistException {
        if (metadata.templates().containsKey(code())) {
            return metadata.templates().get(code());
        }
        throw new TemplateNotExistException(code());
    }
}
