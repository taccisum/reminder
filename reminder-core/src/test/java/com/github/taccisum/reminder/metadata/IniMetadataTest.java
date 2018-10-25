package com.github.taccisum.reminder.metadata;

import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tac
 * @since 25/10/2018
 */
public class IniMetadataTest {
    @Test
    public void init() throws Exception {
        IniMetadata metadata = new IniMetadata("classpath:" + Paths.get("ini", "init.ini").toString());
        assertThat(metadata.templates().get("FOO").getTopic()).isEqualTo("foo topic");
        assertThat(metadata.templates().get("FOO").getBody()).isEqualTo("hello {name}!");
        assertThat(metadata.templates().get("BAR").getTopic()).isEqualTo("bar topic");
        assertThat(metadata.templates().get("BAR").getBody()).isEqualTo("hi {name}!");
    }
}
