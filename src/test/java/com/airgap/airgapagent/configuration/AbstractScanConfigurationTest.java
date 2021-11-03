package com.airgap.airgapagent.configuration;

import com.beust.jcommander.ParameterException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 11/2/2021.
 */
class AbstractScanConfigurationTest {

    @Test
    void convertDate() {
        DateConverter converter =
                new DateConverter("variable");
        Date date = converter.convert("1980:09:15-12:10:12");
        Assertions.assertThat(date).isNotNull();
    }


    @Test
    void convertInvalidDate() {
        DateConverter converter =
                new DateConverter("variable");

        assertThrows(ParameterException.class, () -> converter.convert("1980:09:15 12:10:12"));

    }
}
