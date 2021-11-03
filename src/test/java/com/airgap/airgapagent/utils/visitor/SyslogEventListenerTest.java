package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.service.ErrorService;
import com.airgap.airgapagent.service.syslog.SyslogService;
import com.airgap.airgapagent.utils.DataReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
class SyslogEventListenerTest {

    @Test
    void onFound() throws IOException {

        SyslogService syslogService = Mockito.mock(SyslogService.class);
        Mockito.doAnswer(invocation -> {
            Assertions.assertThat(invocation.getArgument(0).toString()).containsSubsequence("\"source\"=\"file\"");
            Assertions.assertThat(invocation.getArgument(0).toString()).containsSubsequence("\"occurrences\"=\"1\"");
            return null;
        }).when(syslogService).send(Mockito.any());

        ErrorService errorService = Mockito.mock(ErrorService.class);
        SyslogEventListener<String> listener = new SyslogEventListener<>(syslogService, errorService);
        ExactMatchResult<String> result = new ExactMatchResult<>(new DataReader<>("file",
                Map.of(),
                null), 1);
        listener.onFound(null, result);
        Mockito.verify(syslogService, Mockito.atLeastOnce()).send(Mockito.anyString());

    }

    @Test
    void onFoundThrow() throws IOException {

        SyslogService syslogService = Mockito.mock(SyslogService.class);
        Mockito.doThrow(new IOException("test"))
                .when(syslogService).send(Mockito.anyString());

        ErrorService errorService = Mockito.mock(ErrorService.class);
        Mockito.doAnswer(invocation -> {
            Assertions.assertThat(invocation.getArgument(0).toString()).hasToString("file");
            return null;
        }).when(errorService).error(Mockito.any(), Mockito.any(), Mockito.any());
        SyslogEventListener<String> listener = new SyslogEventListener<>(syslogService, errorService);
        ExactMatchResult<String> result = new ExactMatchResult<>(new DataReader<>("file",
                Map.of(),
                null), 1);
        listener.onFound(null, result);


        Mockito.verify(syslogService, Mockito.atLeastOnce()).send(Mockito.anyString());

    }

}
