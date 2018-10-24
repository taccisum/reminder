package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public class IniMetadata implements Metadata {
    private String iniPath;
    private boolean initFlag = false;

    public IniMetadata(String iniPath) {
        this.iniPath = iniPath;
    }

    @Override
    public int init() {
        throw new NotImplementedException();
    }

    @Override
    public int reload() {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, MessageTemplate> templates() {
        throw new NotImplementedException();
    }
}
