package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import com.airgap.airgapagent.service.VisitRepositoryService;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 4/28/2020.
 */
@Service
public class FolderItemReader implements ItemReader<PathInfo>, ItemStream {

    private static final Logger log = LoggerFactory.getLogger(FolderItemReader.class);
    private final VisitRepositoryService repositoryService;
    private Path rootFolder;
    private Visit last;


    @Autowired
    public FolderItemReader(VisitRepositoryService repository) {
        this.repositoryService = repository;
    }

    public void addFolder(Path folder) {
        this.rootFolder = folder;
    }

    @Override
    public PathInfo read() throws Exception {
        last = null;
        if (repositoryService.size() == 0) {
            return null;
        }
        while (repositoryService.size() != 0) {
            Visit current = repositoryService.pop();
            log.debug("Reading : {}", current);

            Path currentPath = Path.of(current.getPath());
            if (Files.isRegularFile(currentPath)) {
                last = current;
                return new PathInfo(rootFolder, currentPath);
            }

            Stream<Path> stream = Files.list(currentPath);
            try (stream) {
                stream.forEach(p -> repositoryService.push(new Visit(current.getScanId(), p)));
            }

        }
        return null;
    }

    @Override
    public void open(@NonNull ExecutionContext executionContext) {
        Scan scan = repositoryService.getRunningScan(rootFolder);
        if (scan == null) {
            scan = repositoryService.newScan(rootFolder);
        }
        repositoryService.push(new Visit(scan, rootFolder));
    }


    @Override
    public void update(@NonNull ExecutionContext executionContext) {
        if (last != null) {
            last.setState(VisitState.VISITED);
            repositoryService.update(last);
        }
    }

    @Override
    public void close() {
        //Nothing to do
    }
}
