package com.airgap.airgapagent.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.File;
import java.util.Arrays;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
@Service
public class FileWalkerService {

    public static final FileWalkerFilter TRUE = f -> true;
    public static final FileWalkerFilter FALSE = f -> false;

    public Flux<File> listFiles(FileWalkerContext fileWalkerContext) {
        File lastFileToVisit = fileWalkerContext.getRefefenceFile();

        return Flux.<File>create(fluxSink -> visit(fileWalkerContext, lastFileToVisit, fluxSink, fileWalkerContext.getRootFile()))
                .doOnNext(fileWalkerContext::setLastFileToVisit);


    }

    private void visit(FileWalkerContext fileWalkerContext, File lastFileToVisit, FluxSink<File> fluxSink, File rootFile) {
        recursiveVisit(fluxSink, fileWalkerContext, rootFile, lastFileToVisit);
        fluxSink.complete();
    }

    private void recursiveVisit(FluxSink<File> fluxSink, FileWalkerContext walkerState, File file, File lastFileToVisit) {
        //If the last file to visit is bigger than the file, it means we can restart where we left
        if (lastFileToVisit != null && file.isFile() && lastFileToVisit.compareTo(file) > 0) {
            return;
        }
        if (file.isFile() && walkerState.getFileFilter().accept(file)) {
            fluxSink.next(file);
        } else {
            if (walkerState.getDirectoryFilter().accept(file)) {
                File[] children = file.listFiles();
                if (children != null) {
                    Arrays.stream(children).sorted().forEach(f -> recursiveVisit(fluxSink, walkerState, f, lastFileToVisit));
                }
            }
        }
    }

}
