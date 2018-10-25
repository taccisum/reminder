package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;

import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public class FixedMetadata implements Metadata {
    private Map<String, MessageTemplate> templates;

    public FixedMetadata() {
    }

    public FixedMetadata(Map<String, MessageTemplate> templates) {
        this.templates = templates;
    }

    public void put(String code, MessageTemplate template) {
        templates.put(code, template);
    }

    @Override
    public int init() {
        return templates.size();
    }

    @Override
    public int reload() {
        return templates.size();
    }

    @Override
    public Map<String, MessageTemplate> templates() {
        return templates;
    }
}
