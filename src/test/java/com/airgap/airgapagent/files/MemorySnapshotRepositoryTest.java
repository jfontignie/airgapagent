package com.airgap.airgapagent.files;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
class MemorySnapshotRepositoryTest {

    @Test
    void getLastSnapshot() {
        MemorySnapshotRepository repository = new MemorySnapshotRepository();
        Assertions.assertThat(repository.getLastSnapshot(null)).isNull();
        Assertions.assertThat(repository.getLastSnapshot(Path.of("."))).isNull();
        Assertions.assertThat(repository.getLastSnapshot(Path.of("."))).isNull();
    }

    @Test
    void saveSnapshot() {

        MemorySnapshotRepository repository = new MemorySnapshotRepository();
        Assertions.assertThat(repository.getLastSnapshot(null)).isNull();
        repository.saveSnapshot(Path.of("."), new SnapshotNode(null));
        Assertions.assertThat(repository.getLastSnapshot(Path.of("."))).isNotNull();

    }
}
