package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.exception.MetadataException;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TemplateMessageBuilder implements MessageBuilder {
    protected Metadata metadata;

    public TemplateMessageBuilder(Metadata metadata) {
        this.metadata = metadata;
    }

    protected MessageTemplate getTemplate() throws MetadataException {
        return metadata.template(code());
    }
}
