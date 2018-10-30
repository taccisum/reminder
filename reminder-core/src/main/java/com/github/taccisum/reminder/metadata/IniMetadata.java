package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import com.github.taccisum.reminder.exception.MetadataException;
import com.github.taccisum.reminder.message.SimpleMessageTemplate;
import com.github.taccisum.reminder.utils.MapUtils;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public class IniMetadata extends AbstractMetadata {
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
        // TODO:: 优化 + 单元测试
        InputStream is = null;
        try {
            if (iniPath.startsWith("classpath:")) {
                is = this.getClass().getClassLoader().getResource(iniPath.substring("classpath:".length(), iniPath.length())).openStream();
            } else {
                is = new FileInputStream(iniPath);
            }
            Wini ini = new Wini(is);
            for (String key : ini.keySet()) {
                Profile.Section section = ini.get(key);
                String topic = section.get("topic");
                String body = section.get("body");
                MapUtils.putOrThrow(templates, section.getName(), new SimpleMessageTemplate(key, topic, body),
                        new MetadataException(String.format("message template \"%s\" is existed", section.getName())));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
