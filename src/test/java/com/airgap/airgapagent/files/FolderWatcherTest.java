package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;
import com.airgap.airgapagent.watch.FolderWatcher;
import com.airgap.airgapagent.watch.MemorySnapshotRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
class FolderWatcherTest {

    @Test
    void scan() throws IOException {
        MemorySnapshotRepository repository = new MemorySnapshotRepository();
        FolderWatcher watcher = new FolderWatcher(Path.of("target"), repository);
        List<Difference> result = watcher.scan();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        result = watcher.scan();
        Assertions.assertThat(result).hasSize(0);

        Files.createDirectory(Path.of("target/directory" + FileUtils.buildTimestamp()));

        result = watcher.scan();
        Assertions.assertThat(result).hasSize(1);

        Assertions.assertThat(repository.count()).isEqualTo(3);

    }

}
