package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.exception.MetadataException;
import com.github.taccisum.reminder.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 2018/10/30
 */
public abstract class AbstractMetadata implements Metadata {
    protected boolean initFlag = false;
    protected Map<String, MessageTemplate> templates = new HashMap<>();

    @Override
    public int init() {
        int i = load();
        initFlag = true;
        return i;
    }

    @Override
    public int reload() {
        templates.clear();
        return load();
    }

    @Override
    public Map<String, MessageTemplate> templates() {
        if (!initFlag) {
            init();
        }
        return templates;
    }

    @Override
    public MessageTemplate template(String code) {
        return MapUtils.getOrThrow(templates(), code, new MetadataException(String.format("message template \"%s\" does not exist", code)));
    }

    protected abstract int load();
}
