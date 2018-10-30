package com.github.taccisum.reminder.selector;

import com.github.taccisum.reminder.api.Target;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2018/10/30
 */
public class FixedTargetSelectorTest {
    @Test
    public void select() throws Exception {
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Target target = mock(Target.class);
            when(target.getId()).thenReturn(i);
            targets.add(target);
        }
        FixedTargetSelector selector = new FixedTargetSelector("FOO", targets);
        List<Target> select = selector.select();
        assertThat(select.size()).isEqualTo(3);
        assertThat(select.get(0).getId()).isEqualTo(0);
        assertThat(select.get(1).getId()).isEqualTo(1);
        assertThat(select.get(2).getId()).isEqualTo(2);
    }
}