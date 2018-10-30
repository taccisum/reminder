package com.github.taccisum.reminder.metadata;

import com.github.taccisum.reminder.api.MessageTemplate;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tac
 * @since 2018/10/30
 */
public class FixedMetadataTest {
    @Test
    public void templates() throws Exception {
        HashMap<String, MessageTemplate> templates = new HashMap<>();
        assertThat(new FixedMetadata(templates).templates()).isEqualTo(templates);
    }
}