package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro.work
 * Created by Jacques Fontignie on 5/10/2020.
 */
class DeleteWorkTest {

    @Test
    void call() throws IOException {
        String pathname = "target/todelete";
        FileUtils.copyDirectory(ConstantsTest.SAMPLE_FOLDER,
                new File(pathname));
        Assertions.assertThat(new File(pathname)).exists();

        DeleteWork work = new DeleteWork();
        work.call(PathInfo.of(pathname));


        FileUtils.copyFile(new File("src/test/resources/sample/sample.docx"),
                new File(pathname));
        Assertions.assertThat(new File(pathname)).exists();

        work.call(PathInfo.of(pathname));
        Assertions.assertThat(new File(pathname)).doesNotExist();


    }
}
