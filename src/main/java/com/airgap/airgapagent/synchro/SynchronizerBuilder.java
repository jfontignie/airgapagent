package com.airgap.airgapagent.synchro;

import java.nio.file.Path;

public class SynchronizerBuilder {
    private Path baseFolder;
    private int earlierThan;
    private Work work;

    public SynchronizerBuilder setBaseFolder(Path baseFolder) {
        this.baseFolder = baseFolder;
        return this;
    }

    public SynchronizerBuilder setEarlierThan(int earlierThan) {
        this.earlierThan = earlierThan;
        return this;
    }

    public SynchronizerBuilder setWork(Work work) {
        this.work = work;
        return this;
    }

    public SynchronizerBuilder setBaseFolder(String path) {
        return setBaseFolder(Path.of(path));
    }

    public Synchronizer createSynchronizer() {
        return new Synchronizer(baseFolder, earlierThan, work);
    }

}
