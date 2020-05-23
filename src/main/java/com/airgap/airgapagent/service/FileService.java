package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.AirFile;
import com.airgap.airgapagent.domain.AirFolder;
import com.airgap.airgapagent.domain.FileCopy;
import reactor.function.Consumer;

import java.io.InputStream;
import java.time.Instant;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
public interface FileService {

    void listFiles(AirFolder folder, Consumer<AirFile> consumer);

    Instant getLastModified(AirFile file);

    boolean hasExtension(AirFile file, String... extensions);

    void copy(AirFolder sourceFolder, AirFile file, AirFolder targetFolder, FileCopy timestamp);

    InputStream getContent(AirFile file);
}
