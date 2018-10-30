package com.github.taccisum.reminder.utils;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author tac
 * @since 2018/10/30
 */
public class MapUtilsTest {
    @Test
    public void getOrThrow() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("foo", new Object());
        MapUtils.getOrThrow(map, "foo", new RuntimeException());
    }

    @Test(expected = RuntimeException.class)
    public void getOrThrowWhenNotExist() throws Exception {
        MapUtils.getOrThrow(new HashMap<>(), "foo", new RuntimeException());
    }

    @Test
    public void putOrThrow() throws Exception {
        MapUtils.putOrThrow(new HashMap<>(), "foo", new Object(), new RuntimeException());
    }

    @Test(expected = RuntimeException.class)
    public void putOrThrowWhenExisted() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("foo", new Object());
        MapUtils.putOrThrow(map, "foo", new Object(), new RuntimeException());
    }
}