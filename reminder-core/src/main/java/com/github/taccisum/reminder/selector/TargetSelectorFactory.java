package com.github.taccisum.reminder.selector;

import com.github.taccisum.reminder.api.TargetSelector;
import com.github.taccisum.reminder.exception.TargetSelectorFactoryException;
import com.github.taccisum.reminder.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TargetSelectorFactory {
    private static Map<String, TargetSelector> selectors = new HashMap<>();

    public static void put(TargetSelector selector) {
        MapUtils.putOrThrow(selectors, selector.code(), selector, new TargetSelectorFactoryException(String.format("target selector \"%s\" is existed", selector.code())));
    }

    public static TargetSelector create(String remindCode) {
        return MapUtils.getOrThrow(selectors, remindCode, new TargetSelectorFactoryException(String.format("target selector \"%s\" does not exist", remindCode)));
    }

    public static void clear() {
        selectors.clear();
    }
}
