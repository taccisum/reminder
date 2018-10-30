package com.github.taccisum.reminder.utils;

import java.util.Map;

/**
 * @author tac
 * @since 2018/10/30
 */
public abstract class MapUtils {
    public static <T> T getOrThrow(Map<String, T> map, String key, RuntimeException throwWhenNotExist) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw throwWhenNotExist;
    }

    public static <T> void putOrThrow(Map<String, T> map, String key, T val, RuntimeException throwWhenExisting) {
        if (map.containsKey(key)) {
            throw throwWhenExisting;
        }
        map.put(key, val);
    }
}
