package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.CsvWriter;
import com.airgap.airgapagent.utils.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class CSVWriterEventListener<T> extends SearchEventAdapter<T> implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(CSVWriterEventListener.class);
    private final Serializer<T> serializer;
    private CsvWriter csvWriter;

    public CSVWriterEventListener(File foundLocation,
                                  Set<String> exclude,
                                  Serializer<T> serializer) throws IOException {
        csvWriter = new CsvWriter(foundLocation, exclude);
        this.serializer = serializer;
    }

    @Override
    public void onFound(CrawlState<T> crawlState, ExactMatchResult<T> result) {
        csvWriter.save(result, serializer);
    }

    @Override
    public void onClose(CrawlState<T> state) {
        this.close();
    }

    @Override
    public void close() {
        if (csvWriter == null) {
            return;
        }
        try {
            csvWriter.close();
        } catch (IOException e) {
            log.error("Impossible to close the CSV writer due to error", e);
        }
        csvWriter = null;

    }
}
