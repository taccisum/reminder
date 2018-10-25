package com.github.taccisum.reminder.api;

import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public interface TargetSelector extends Unique {
    List<Target> select(Object... args);
}
