package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;
import com.airgap.airgapagent.watch.SnapshotDifferentiator;
import com.airgap.airgapagent.watch.SnapshotMaker;
import com.airgap.airgapagent.watch.SnapshotNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
class SnapshotDifferentiatorTest {

    @Test
    void getDifferences() throws IOException {

        Path pathToDelete = Path.of("target/created" + FileUtils.buildTimestamp());
        Path pathToCreate = Files.createFile(Path.of("target/deleted" + FileUtils.buildTimestamp()));
        Path pathToModify = Files.createFile(Path.of("target/modified" + FileUtils.buildTimestamp()));
        Set<Path> changes = new HashSet<>();
        changes.add(pathToCreate);
        changes.add(pathToDelete);
        changes.add(pathToModify);

        SnapshotMaker maker = new SnapshotMaker(Path.of("target"));
        SnapshotNode snapshot = maker.visit();

        List<Difference> differences;
        differences = new SnapshotDifferentiator().getDifferences(snapshot, snapshot);

        Assertions.assertThat(differences).isEmpty();

        //Modify objects
        Files.createFile(pathToDelete);
        Files.writeString(pathToModify, "modified");
        Files.delete(pathToCreate);

        //Check differences
        SnapshotNode snapshot2 = maker.visit();

        differences = new SnapshotDifferentiator().getDifferences(snapshot, snapshot2);
        System.out.println(differences);
        Assertions.assertThat(differences).hasSize(3);

        for (Difference difference : differences) {
            boolean found = changes.remove(difference.getReference().getData().getPath());
            Assertions.assertThat(found).isTrue();
        }

        Assertions.assertThat(changes).isEmpty();


    }

    @Test
    public void testEmptySnapshot() {
        SnapshotDifferentiator differentiator = new SnapshotDifferentiator();
        List<Difference> difference1 = differentiator.getDifferences(null, new SnapshotNode(null));
        Assertions.assertThat(difference1).hasSize(1);


    }
}
