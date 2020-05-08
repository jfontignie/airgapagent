package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.Repo;
import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import com.airgap.airgapagent.repository.RepoRepository;
import com.airgap.airgapagent.repository.ScanRepository;
import com.airgap.airgapagent.repository.VisitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Service
public class VisitRepositoryService {

    private static final Logger log = LoggerFactory.getLogger(VisitRepositoryService.class);

    private final VisitRepository visitRepository;
    private final RepoRepository repoRepository;
    private final ScanRepository scanRepository;

    public VisitRepositoryService(RepoRepository repoRepository, ScanRepository scanRepository, VisitRepository repository) {
        this.repoRepository = repoRepository;
        this.scanRepository = scanRepository;
        this.visitRepository = repository;
    }

    public void push(Visit visit) {
        log.trace("Pushing {}", visit);
        visitRepository.save(visit);
    }


    public Visit popNext(Scan scan) {
        List<Visit> visits = visitRepository.findByScanAndStateOrderByUpdatedDesc(scan, VisitState.TODO);
        log.trace("Popping {}", visits);
        if (!visits.isEmpty()) {
            Visit visit = visits.get(0);
            visitRepository.delete(visit);
            return visit;
        }
        return null;
    }

    public Scan getRunningScan(Path rootFolder) {
        log.info("Looking for repository with name {}", rootFolder);
        List<Repo> repo = repoRepository.findByPath(sanitize(rootFolder));
        if (repo.isEmpty()) return null;

        List<Scan> scan = scanRepository.findRunningScanByRepo(repo.get(0), VisitState.TODO);
        log.info("Found scan ongoing: {}", scan.size());
        return scan.isEmpty() ? null : scan.get(0);
    }

    private String sanitize(Path folder) {
        return folder.toString().toLowerCase();
    }

    public Scan newScan(Path rootFolder) {
        List<Repo> repos = repoRepository.findByPath(sanitize(rootFolder));
        Repo repo;
        if (repos.isEmpty()) {
            repo = new Repo();
            repo.setPath(sanitize(rootFolder));
            repoRepository.save(repo);
        } else {
            repo = repos.get(0);
        }
        Objects.requireNonNull(repo.getId(), "Repo should have been set");
        Scan scan = new Scan();
        scan.setRepo(repo);
        scanRepository.save(scan);
        return scan;
    }

    public @Nullable
    Visit findPreviousScanOfPath(Scan scan, Path currentPath) {
        List<Visit> visits = visitRepository.findByScanNotEqualsAndPathEqualsOrderByUpdatedDesc(scan, sanitize(currentPath));
        return visits.isEmpty() ? null : visits.get(0);
    }
}
