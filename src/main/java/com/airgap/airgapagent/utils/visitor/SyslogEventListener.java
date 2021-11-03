package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.service.ErrorService;
import com.airgap.airgapagent.service.syslog.SyslogFormatter;
import com.airgap.airgapagent.service.syslog.SyslogService;
import com.airgap.airgapagent.utils.CrawlState;

import java.io.IOException;
import java.util.Map;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class SyslogEventListener<T> extends SearchEventAdapter<T> {
    private final SyslogService syslogService;
    private final ErrorService errorService;

    public SyslogEventListener(SyslogService syslogService, ErrorService errorService) {
        this.syslogService = syslogService;
        this.errorService = errorService;
    }

    @Override
    public void onFound(CrawlState<T> crawlState,
                        ExactMatchResult<T> result) {
        SyslogFormatter formatter = new SyslogFormatter(Map.of(
                "source", result.getDataSource().getSource().toString(),
                "occurrences", String.valueOf(result.getOccurrences())
        ));
        formatter.add(result.getDataSource().getMetadata());

        try {
            syslogService.send(formatter.toString());
        } catch (IOException e) {
            errorService.error(result.getDataSource().getSource(), "Impossible to send syslog", e);
        }

    }
}
