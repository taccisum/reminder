package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.exception.MetadataException;
import com.github.taccisum.reminder.utils.MapUtils;

/**
 * @author tac
 * @since 2018/10/30
 */
public abstract class AbstractMetadata implements Metadata {
    @Override
    public MessageTemplate template(String code) {
        return MapUtils.getOrThrow(templates(), code, new MetadataException(String.format("message template \"%s\" does not exist", code)));
    }
}
