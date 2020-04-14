package com.airgap.airgapagent.flows.trigger;

import com.airgap.airgapagent.files.Watcher;
import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.work.WorkReport;
import com.airgap.airgapagent.flows.workflow.WorkFlow;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * com.airgap.airgapagent.flows.trigger
 * Created by Jacques Fontignie on 4/14/2020.
 */
public class FileTrigger implements Trigger {
    private final WorkFlow workFlow;
    private final Watcher watcher;
    private WatchService watchService;

    public FileTrigger(Watcher watcher, WorkFlow workFlow) {
        this.watcher = watcher;
        this.workFlow = workFlow;
    }

    @Override
    public WorkReport take() throws InterruptedException {
        if (watchService == null) {
            throw new IllegalStateException("The trigger has not been initiated");
        }
        WatchKey key = watchService.take();
        WorkContext workContext = new WorkContext();
        Set<Path> filesToWatch = key.pollEvents().stream()
                .filter(e -> e.kind().equals(ENTRY_CREATE) || e.kind().equals(ENTRY_MODIFY))
                .map(e -> (Path) e.context())
                .collect(Collectors.toUnmodifiableSet());
        workContext.put("files", filesToWatch);
        return workFlow.call(workContext);
    }

    @Override
    public boolean poll() {
        if (watchService == null) {
            throw new IllegalStateException("The trigger has not been initiated");
        }
        WatchKey key = watchService.poll();
        return key != null;
    }

    @Override
    public void init() throws IOException {
        watchService = watcher.watch();
    }

    public static class Builder {
        private Watcher watcher;
        private WorkFlow workFlow;

        public static Builder aNewFileTrigger() {
            return new Builder();
        }

        public Builder setWatcher(Watcher watcher) {
            this.watcher = watcher;
            return this;
        }

        public Builder then(WorkFlow build) {
            this.workFlow = build;
            return this;
        }

        public Trigger build() {
            return new FileTrigger(watcher, workFlow);
        }

    }
}
