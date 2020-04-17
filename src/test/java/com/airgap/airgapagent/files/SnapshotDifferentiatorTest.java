package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;
import com.airgap.airgapagent.watch.SnapshotDifferentiator;
import com.airgap.airgapagent.watch.SnapshotMaker;
import com.airgap.airgapagent.watch.SnapshotNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
    @Disabled
    void getDifferences() throws IOException {

        String fileToDelete = "created" + FileUtils.buildTimestamp();
        String fileToCreate = "deleted" + FileUtils.buildTimestamp();
        String fileToModify = "modified" + FileUtils.buildTimestamp();
        Path pathToDelete = Path.of("target/", fileToDelete);
        Path pathToCreate = Files.createFile(Path.of("target/", fileToCreate));
        Path pathToModify = Files.createFile(Path.of("target/", fileToModify));
        Set<String> changes = new HashSet<>();
        changes.add(fileToCreate);
        changes.add(fileToDelete);
        changes.add(fileToModify);

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
            boolean found = changes.remove(difference.getReference().getData().getRelative());
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
