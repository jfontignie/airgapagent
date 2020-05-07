package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.synchro.work.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 4/27/2020.
 */
public class PathInfoProcessor implements ItemProcessor<PathInfo, PathInfo>, ItemStream {
    private static final Logger log = LoggerFactory.getLogger(PathInfoProcessor.class);
    private final Work rootWork;

    public PathInfoProcessor(Work rootWork) {
        this.rootWork = rootWork;
    }

    @Override
    public PathInfo process(@NonNull PathInfo item) throws Exception {
        log.debug("Receiving pathInfo item: {}", item);
        rootWork.call(item);
        return item;
    }


    @Override
    public void open(@NonNull ExecutionContext executionContext) {
        try {
            rootWork.init();
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public void update(@Nullable ExecutionContext executionContext) {
        //Nothing to do
    }

    @Override
    public void close() {
        try {
            rootWork.close();
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
    }
}
