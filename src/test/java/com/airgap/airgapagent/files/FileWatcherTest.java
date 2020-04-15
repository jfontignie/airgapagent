package com.airgap.airgapagent.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.EnumSet;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/14/2020.
 */
class FileWatcherTest {

    @Test
    void watch() throws IOException, InterruptedException {
        FileWatcher watcher = FileWatcher.Builder.aNewWatch(Path.of("target/")).build();
        WatchService watchService = watcher.watch();
        WatchKey key = watchService.poll();
        Assertions.assertNull(key);
        File f = new File("target/watch" + FileUtils.buildTimestamp());
        Assertions.assertTrue(f.createNewFile());
        key = watchService.take();
        Assertions.assertTrue(key.isValid());
        key.pollEvents()
                .forEach(e ->
                        System.out.println(String.format("Event: %s on %s (%d)", e.kind(), e.context(), e.count())));
        Assertions.assertTrue(key.isValid());

        f = new File("target/watch" + FileUtils.buildTimestamp());

        Assertions.assertTrue(f.createNewFile());
        Assertions.assertTrue(key.isValid());
        key.reset();
        watchService.take();
        key.pollEvents()
                .forEach(e ->
                        System.out.println(String.format("Event: %s on %s (%d)", e.kind(), e.context(), e.count())));

    }

    @Test
    void testBuilder() {
        FileWatcher watcher = FileWatcher
                .Builder
                .aNewWatch(Path.of("target/"))
                .setWatchTypes(EnumSet.noneOf(WatchType.class))
                .build();
        Assertions.assertNotNull(watcher);
        Assertions.assertEquals(Path.of("target/"),watcher.getPath());
    }
}
