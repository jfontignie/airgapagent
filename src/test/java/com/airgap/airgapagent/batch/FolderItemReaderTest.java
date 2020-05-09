package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import com.airgap.airgapagent.service.FileWrapperService;
import com.airgap.airgapagent.service.VisitRepositoryService;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/9/2020.
 */
class FolderItemReaderTest {

    private static final Path TEST_PATH = Path.of("test");
    private FolderItemReader folderItemReader;
    private FileWrapperService fileWrapperService;
    private VisitRepositoryService visitRepositoryService;
    private Scan scan;

    @BeforeEach
    public void setUp() {
        scan = new Scan();

        Answer not_implemented = invocation -> {
            throw new IllegalStateException("not implemented : " +
                    invocation.getMock().getClass().getName() + "->" + invocation.getMethod().getName());
        };
        fileWrapperService = Mockito.mock(FileWrapperService.class, not_implemented);
        visitRepositoryService = Mockito.mock(VisitRepositoryService.class);
        folderItemReader = new FolderItemReader(fileWrapperService, visitRepositoryService);
    }

    @Test
    void initExistingScan() {
        scan = new Scan();
        Mockito.doReturn(scan).when(visitRepositoryService).getRunningScan(TEST_PATH);
        Visit visit = new Visit(scan, TEST_PATH);
        Mockito.doNothing().when(visitRepositoryService).push(visit);

        folderItemReader.setFolder(TEST_PATH);
        folderItemReader.init();
        Mockito.verify(visitRepositoryService, Mockito.atLeastOnce()).push(visit);
    }

    @Test
    void initNonExistingScan() {
        Mockito.doReturn(null).when(visitRepositoryService).getRunningScan(TEST_PATH);
        Visit visit = new Visit(scan, TEST_PATH);
        Mockito.doReturn(scan).when(visitRepositoryService).newScan(TEST_PATH);
        Mockito.doNothing().when(visitRepositoryService).push(visit);


        folderItemReader.setFolder(TEST_PATH);
        folderItemReader.init();
        Mockito.verify(visitRepositoryService, Mockito.atLeastOnce()).push(visit);
    }

    @Test
    void callFileWithNoPreviousScan() throws Exception {
        initExistingScan();
        PathInfo pathInfo = new PathInfo("test", "file");
        Visit visit = new Visit();
        Path file = Path.of("file");
        visit.setPath(file);

        Mockito.doReturn(visit).when(visitRepositoryService).popNext(scan);
        Mockito.doReturn(true).when(fileWrapperService).isRegularFile(file);
        Mockito.doReturn(null).when(visitRepositoryService).findPreviousScanOfPath(scan, file);
        Mockito.doNothing().when(visitRepositoryService).push(visit);

        PathInfo found = folderItemReader.read();
        Assertions.assertEquals(pathInfo, found);

    }

    @Test
    void callFolderEmpty() throws Exception {
        initExistingScan();
        Visit visit = new Visit();

        Path folder = Path.of("folder");
        visit.setPath(folder);

        Mockito.when(visitRepositoryService.popNext(scan))
                .thenReturn(visit)
                .thenReturn(null);

        Mockito.doReturn(false).when(fileWrapperService).isRegularFile(folder);
        Mockito.doReturn(Lists.list(Path.of("subfolder"))).when(fileWrapperService).list(folder);

        PathInfo found = folderItemReader.read();
        Assertions.assertNull(found);
    }


    @Test
    void callFileWithPreviousScan() throws Exception {
        initExistingScan();
        PathInfo pathInfo = new PathInfo("test", "file");
        Visit visit = new Visit();
        Path file = Path.of("file");
        visit.setPath(file);
        Visit firstVisit = new Visit();
        firstVisit.setState(VisitState.VISITED);
        Instant instant = LocalDate.parse("2000-01-01").atStartOfDay().toInstant(ZoneOffset.UTC);
        firstVisit.setUpdated(instant);

        Visit secondVisit = new Visit();
        secondVisit.setState(VisitState.VISITED);

        instant = LocalDate.parse("1000-01-01").atStartOfDay().toInstant(ZoneOffset.UTC);
        secondVisit.setUpdated(instant);

        Mockito.doReturn(visit).when(visitRepositoryService).popNext(scan);
        Mockito.doReturn(true).when(fileWrapperService).isRegularFile(file);

        Mockito.when(visitRepositoryService.findPreviousScanOfPath(scan, file))
                .thenReturn(firstVisit)
                .thenReturn(secondVisit);

        Mockito.doNothing().when(visitRepositoryService).push(visit);

        instant = LocalDate.parse("1500-01-01").atStartOfDay().toInstant(ZoneOffset.UTC);

        Mockito.doReturn(FileTime.from(instant)).when(fileWrapperService).getLastModifiedTime(file);

        PathInfo found = folderItemReader.read();
        Assertions.assertEquals(pathInfo, found);

    }
}
