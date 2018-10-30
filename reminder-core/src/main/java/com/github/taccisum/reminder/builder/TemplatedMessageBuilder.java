package com.github.taccisum.reminder.builder;

import com.github.taccisum.reminder.api.MessageBuilder;
import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.exception.MetadataException;
import com.github.taccisum.reminder.utils.MapUtils;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TemplatedMessageBuilder implements MessageBuilder {
    protected Metadata metadata;

    public TemplatedMessageBuilder(Metadata metadata) {
        this.metadata = metadata;
    }

    protected MessageTemplate getTemplate() throws MetadataException {
        return MapUtils.getOrThrow(metadata.templates(), code(), new MetadataException(String.format("message template \"%s\" does not exist", code())));
    }
}
