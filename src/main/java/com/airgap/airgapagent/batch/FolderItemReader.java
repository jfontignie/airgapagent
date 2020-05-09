package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import com.airgap.airgapagent.service.FileWrapperService;
import com.airgap.airgapagent.service.VisitRepositoryService;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Objects;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 4/28/2020.
 */
@Service
public class FolderItemReader implements ItemReader<PathInfo> {

    private static final Logger log = LoggerFactory.getLogger(FolderItemReader.class);
    private final VisitRepositoryService repositoryService;
    private final FileWrapperService fileWrapperService;
    private Path rootFolder;
    private Scan scan;


    @Autowired
    public FolderItemReader(
            FileWrapperService fileWrapperService,
            VisitRepositoryService repository) {
        this.repositoryService = repository;
        this.fileWrapperService = fileWrapperService;
    }

    public void setFolder(Path folder) {
        this.rootFolder = folder;
        init();
    }

    @Override
    public PathInfo read() throws Exception {
        Objects.requireNonNull(scan, "Scan must be set");
        Visit current;
        while ((current = repositoryService.popNext(scan)) != null) {
            log.trace("Reading : {}", current);


            Path currentPath = Path.of(current.getPath());
            if (fileWrapperService.isRegularFile(currentPath)) {
                //Important to get the last scan before pushing
                Visit v = repositoryService.findPreviousScanOfPath(scan, currentPath);


                if (v != null
                        && v.getState() == VisitState.VISITED
                        && v.getUpdated().compareTo(fileWrapperService.getLastModifiedTime(currentPath).toInstant()) > 0) {
                    log.debug("Already scanned: {}", currentPath);
                    continue;
                }

                log.debug("Analysing file : {}", currentPath);

                current.setState(VisitState.VISITED);
                repositoryService.push(current);
                return new PathInfo(rootFolder, currentPath);

            }

            fileWrapperService.list(currentPath).forEach(p -> repositoryService.push(new Visit(scan, p)));

        }
        return null;
    }

    public void init() {
        Objects.requireNonNull(rootFolder, "Folder has not been set");
        log.info("Looking for running scan");
        Scan foundScan = repositoryService.getRunningScan(rootFolder);
        if (foundScan == null) {
            log.info("No scan found");
            foundScan = repositoryService.newScan(rootFolder);
        }
        this.scan = foundScan;
        repositoryService.push(new Visit(foundScan, rootFolder));
    }

}
