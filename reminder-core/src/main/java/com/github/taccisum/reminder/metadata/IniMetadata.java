package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.api.Metadata;
import com.github.taccisum.reminder.message.SimpleMessageTemplate;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public class IniMetadata implements Metadata {
    private String iniPath;
    private boolean initFlag = false;
    private Map<String, MessageTemplate> templates = new HashMap<>();

    public IniMetadata(String iniPath) {
        this.iniPath = iniPath;
    }

    @Override
    public int init() {
        int i = load();
        initFlag = true;
        return i;
    }

    private int load() {
        try {
            String path = iniPath;
            if (iniPath.startsWith("classpath:")) {
                path = this.getClass().getClassLoader().getResource(iniPath.substring("classpath:".length(), iniPath.length())).getPath();
            }
            Wini ini = new Wini(new File(path));
            for (String key : ini.keySet()) {
                Profile.Section section = ini.get(key);
                String topic = section.get("topic");
                String body = section.get("body");
                templates.put(section.getName(), new SimpleMessageTemplate(key, topic, body));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return templates.size();
    }

    @Override
    public int reload() {
        return load();
    }

    @Override
    public Map<String, MessageTemplate> templates() {
        if (!initFlag) {
            init();
        }
        return templates;
    }
}
