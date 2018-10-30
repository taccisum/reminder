package com.github.taccisum.reminder.metadata;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2018/10/30
 */
public class AbstractMetadataTest {
    private FooMetadata metadata;

    @Before
    public void setUp() throws Exception {
        metadata = spy(new FooMetadata());
    }

    @Test
    public void templates() throws Exception {
        metadata.templates();
        verify(metadata, times(1)).init();
    }

    @Test
    public void multiCallTemplates() throws Exception {
        for (int i = 0; i < 10; i++) {
            metadata.templates();
        }
        verify(metadata, times(1)).init();
    }

    private static class FooMetadata extends AbstractMetadata {
        @Override
        protected int load() {
            return 0;
        }
    }
}