package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;

import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public class FixedMetadata extends AbstractMetadata {
    public FixedMetadata() {
    }

    public FixedMetadata(Map<String, MessageTemplate> templates) {
        this.templates = templates;
    }

    public void put(String code, MessageTemplate template) {
        templates.put(code, template);
    }

    @Override
    public Map<String, MessageTemplate> templates() {
        return templates;
    }

    @Override
    public int reload() {
        return load();
    }

    @Override
    protected int load() {
        return templates.size();
    }
}
