package com.github.taccisum.reminder.selector;

import com.github.taccisum.reminder.api.TargetSelector;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TargetSelectorFactory {
    private static Map<String, TargetSelector> selectors = new HashMap<>();

    public static void put(String remindCode, TargetSelector selector) {
        selectors.put(remindCode, selector);
    }

    public static TargetSelector create(String remindCode) {
        return selectors.get(remindCode);
    }
}
