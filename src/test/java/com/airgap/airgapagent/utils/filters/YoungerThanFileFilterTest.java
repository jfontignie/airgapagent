package com.airgap.airgapagent.utils.filters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;

/**
 * com.airgap.airgapagent.utils.filters
 * Created by Jacques Fontignie on 11/2/2021.
 */
class YoungerThanFileFilterTest {

    @Test
    void acceptYounger() throws IOException {
        YoungerThanFileFilter filter = new YoungerThanFileFilter(Instant.now());
        File younger = File.createTempFile("dat", "dat");
        Assertions.assertThat(filter.accept(younger)).isTrue();

    }

    @Test
    void acceptOlder() throws IOException {

        File older = File.createTempFile("dat", "dat");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        YoungerThanFileFilter filter = new YoungerThanFileFilter(calendar.toInstant());

        Assertions.assertThat(filter.accept(older)).isFalse();
    }
}
