package com.airgap.airgapagent.flows.workflow;

import com.airgap.airgapagent.flows.work.Work;
import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.work.WorkReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.airgap.airgapagent.flows.work.WorkStatus.FAILED;

/**
 * com.airgap.airgapagent.flows.workflow
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class SequentialFlow extends AbstractWorkFlow{
    private static final Logger logger = LoggerFactory.getLogger(SequentialFlow.class);

    private final List<Work> works = new ArrayList<>();

    SequentialFlow(String name, List<Work> works) {
        super(name);
        this.works.addAll(works);
    }

    @Override
    public WorkReport call( WorkContext workContext) {
        WorkReport workReport = null;
        for (Work work : works) {
            workReport = work.call(workContext);
            if (workReport != null && FAILED.equals(workReport.getStatus())) {
                logger.info("Work unit ''{}'' has failed, skipping subsequent work units", work.getName());
                break;
            }
        }
        return workReport;
    }

    public static class Builder {

        private String name;
        private List<Work> works;

        private Builder() {
            this.name = UUID.randomUUID().toString();
            this.works = new ArrayList<>();
        }

        public static SequentialFlow.Builder aNewSequentialFlow() {
            return new SequentialFlow.Builder();
        }

        public SequentialFlow.Builder named(String name) {
            this.name = name;
            return this;
        }

        public SequentialFlow.Builder execute(Work work) {
            this.works.add(work);
            return this;
        }

        public SequentialFlow.Builder then(Work work) {
            this.works.add(work);
            return this;
        }

        public SequentialFlow build() {
            return new SequentialFlow(name, works);
        }
    }
}
