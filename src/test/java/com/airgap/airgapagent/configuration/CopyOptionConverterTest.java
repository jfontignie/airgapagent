package com.airgap.airgapagent.configuration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 11/3/2021.
 */
class CopyOptionConverterTest {

    @Test
    void convert() {
        CopyOptionConverter converter = new CopyOptionConverter();
        Set<CopyOption> options = converter.convert("FLAT_HIERARCHY,RENAME_ON_COLLISION,CLEAN_ON_STARTUP");
        Assertions.assertThat(options).containsAll(Set.of(CopyOption.FLAT_HIERARCHY,
                CopyOption.RENAME_ON_COLLISION,
                CopyOption.CLEAN_ON_STARTUP));
    }
}
