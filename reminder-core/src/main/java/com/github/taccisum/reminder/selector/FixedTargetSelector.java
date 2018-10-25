package com.github.taccisum.reminder.selector;

import com.github.taccisum.reminder.api.Target;
import com.github.taccisum.reminder.api.TargetSelector;

import java.util.List;

/**
 * @author tac
 * @since 24/10/2018
 */
public class FixedTargetSelector implements TargetSelector {
    private List<Target> targets;
    private String code;

    public FixedTargetSelector(String code, List<Target> targets) {
        this.targets = targets;
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public List<Target> select(Object... args) {
        return targets;
    }
}
