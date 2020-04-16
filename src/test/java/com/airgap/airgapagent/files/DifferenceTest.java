package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;
import com.airgap.airgapagent.watch.DifferenceType;
import com.airgap.airgapagent.watch.SnapshotNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
class DifferenceTest {

    @Test
    public void test() {
        Difference diff = new Difference(DifferenceType.MISSING_FIRST,
                null, new SnapshotNode(null));
        Assertions.assertThat(diff.getType()).isEqualTo(DifferenceType.MISSING_FIRST);
        Assertions.assertThat(diff.getReference()).isNotNull();
    }
}
