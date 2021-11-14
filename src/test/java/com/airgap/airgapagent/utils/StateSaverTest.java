package com.airgap.airgapagent.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 11/14/2021.
 */
class StateSaverTest {

    public static final File FILE = new File("target/StateSaverTest");
    public static final String TEST = "test";
    public static final String VALUE = "value";

    @Test
    void set() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        FILE.delete();
        Assertions.assertThat(StateSaver.get(FILE, TEST)).isNull();

        StateSaver.set(FILE, TEST, VALUE);

        Assertions.assertThat(StateSaver.get(FILE, TEST)).isEqualTo(VALUE);
        StateSaver.set(FILE, TEST, null);

        Assertions.assertThat(StateSaver.get(FILE, TEST)).isNull();
        Assertions.assertThat(FILE.delete()).isTrue();
    }
}
