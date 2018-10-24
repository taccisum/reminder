package com.github.taccisum.reminder.selector;

import com.github.taccisum.reminder.api.TargetSelector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author tac
 * @since 24/10/2018
 */
public abstract class TargetSelectorFactory {
    public static TargetSelector create(String remindCode) {
        throw new NotImplementedException();
    }
}
